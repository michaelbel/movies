package org.michaelbel.movies.common.trakt.impl

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketTimeoutException
import java.net.URI
import java.net.URISyntaxException
import java.net.URLDecoder
import java.net.URLEncoder
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.trakt.TraktAuthException
import org.michaelbel.movies.common.trakt.TraktAuthInteractor
import org.michaelbel.movies.common.trakt.TraktAuthResult

class TraktAuthInteractorImpl(
    private val context: Context,
    private val dispatchers: SharedDispatchers
): TraktAuthInteractor {

    private val secureRandom = SecureRandom()

    override val isSupported: Boolean = true

    override suspend fun authorize(clientId: String, redirectUri: String): TraktAuthResult {
        val redirect = try {
            URI(redirectUri)
        } catch (e: URISyntaxException) {
            throw TraktAuthException("Trakt redirect uri is invalid")
        }

        val host = redirect.host
        val port = redirect.port
        val isLoopbackHost = host == "localhost" || host == "127.0.0.1"
        if (redirect.scheme != "http" || !isLoopbackHost || port == -1) {
            throw TraktAuthException("Trakt redirect uri must be http://localhost:<port>/<path>")
        }

        val callbackPath = redirect.rawPath.orEmpty().ifEmpty { "/" }
        val codeVerifier = randomString(CODE_VERIFIER_LENGTH)
        val state = randomString(STATE_LENGTH)
        val digest = MessageDigest.getInstance("SHA-256").digest(codeVerifier.toByteArray(Charsets.US_ASCII))
        // minSdk is 26, so java.util.Base64 is available.
        val codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(digest)

        // URLEncoder.encode(String, Charset) is available only since API 33, so the charset name overload is used.
        val authorizationUrl = buildString {
            append(AUTHORIZE_URL)
            append("?response_type=code")
            append("&client_id=").append(URLEncoder.encode(clientId, UTF_8))
            append("&redirect_uri=").append(URLEncoder.encode(redirectUri, UTF_8))
            append("&state=").append(URLEncoder.encode(state, UTF_8))
            append("&code_challenge=").append(URLEncoder.encode(codeChallenge, UTF_8))
            append("&code_challenge_method=S256")
        }

        return withContext(dispatchers.io) {
            val serverSocket = try {
                ServerSocket(port, BACKLOG, InetAddress.getByName(LOOPBACK_ADDRESS))
            } catch (e: IOException) {
                throw TraktAuthException("Port $port is busy, cannot receive Trakt sign in callback")
            }

            try {
                openBrowser(authorizationUrl)

                val code = suspendCancellableCoroutine { continuation ->
                    continuation.invokeOnCancellation { serverSocket.close() }
                    continuation.resumeWith(runCatching { awaitCode(serverSocket, callbackPath, state) })
                }

                TraktAuthResult(
                    code = code,
                    codeVerifier = codeVerifier
                )
            } finally {
                serverSocket.close()
            }
        }
    }

    private fun randomString(length: Int): String {
        return buildString(length) {
            repeat(length) {
                append(ALPHABET[secureRandom.nextInt(ALPHABET.length)])
            }
        }
    }

    private fun openBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            throw TraktAuthException("Browser is not available on this device")
        }
    }

    private fun awaitCode(serverSocket: ServerSocket, callbackPath: String, expectedState: String): String {
        val deadline = System.nanoTime() + TIMEOUT_MILLIS * NANOS_IN_MILLI

        while (true) {
            val remainingMillis = (deadline - System.nanoTime()) / NANOS_IN_MILLI
            if (remainingMillis <= 0) {
                throw TraktAuthException("Trakt sign in timed out")
            }

            serverSocket.soTimeout = remainingMillis.toInt()

            val socket = try {
                serverSocket.accept()
            } catch (e: SocketTimeoutException) {
                throw TraktAuthException("Trakt sign in timed out")
            } catch (e: IOException) {
                throw TraktAuthException("Trakt sign in was interrupted")
            }

            val code = socket.use { handleConnection(it, callbackPath, expectedState) }
            if (code != null) {
                return code
            }
        }
    }

    private fun handleConnection(socket: Socket, callbackPath: String, expectedState: String): String? {
        val requestLine = try {
            socket.soTimeout = CLIENT_TIMEOUT_MILLIS
            val reader = BufferedReader(InputStreamReader(socket.getInputStream(), Charsets.ISO_8859_1))
            val firstLine = reader.readLine()
            var headerLines = 0
            while (headerLines < MAX_HEADER_LINES && !reader.readLine().isNullOrEmpty()) {
                headerLines++
            }
            firstLine
        } catch (e: IOException) {
            return null
        }

        val parts = requestLine.orEmpty().split(' ')
        if (parts.size < 2 || parts[0] != "GET") {
            writeResponse(socket, "404 Not Found", "")
            return null
        }

        val target = parts[1]
        if (target.substringBefore('?') != callbackPath) {
            writeResponse(socket, "404 Not Found", "")
            return null
        }

        val params = try {
            target.substringAfter('?', "")
                .split('&')
                .filter { it.isNotEmpty() }
                .associate { pair ->
                    val key = URLDecoder.decode(pair.substringBefore('='), UTF_8)
                    val value = URLDecoder.decode(pair.substringAfter('=', ""), UTF_8)
                    key to value
                }
        } catch (e: IllegalArgumentException) {
            writeResponse(socket, "400 Bad Request", "")
            return null
        }

        val state = params["state"]
        val error = params["error"]
        val code = params["code"]
        val isStateValid = state != null &&
            MessageDigest.isEqual(state.toByteArray(Charsets.UTF_8), expectedState.toByteArray(Charsets.UTF_8))

        when {
            !isStateValid -> {
                writeResponse(socket, "200 OK", FAILURE_PAGE)
                throw TraktAuthException("Trakt sign in failed: state mismatch")
            }
            error != null -> {
                writeResponse(socket, "200 OK", FAILURE_PAGE)
                throw TraktAuthException("Trakt sign in failed: ${error.take(MAX_ERROR_LENGTH)}")
            }
            code.isNullOrEmpty() -> {
                writeResponse(socket, "200 OK", FAILURE_PAGE)
                throw TraktAuthException("Trakt sign in failed: authorization code is missing")
            }
        }

        writeResponse(socket, "200 OK", SUCCESS_PAGE)
        return code
    }

    private fun writeResponse(socket: Socket, status: String, body: String) {
        val bodyBytes = body.toByteArray(Charsets.UTF_8)
        val head = "HTTP/1.1 $status\r\n" +
            "Content-Type: text/html; charset=utf-8\r\n" +
            "Content-Length: ${bodyBytes.size}\r\n" +
            "Connection: close\r\n\r\n"

        try {
            val output = socket.getOutputStream()
            output.write(head.toByteArray(Charsets.ISO_8859_1))
            output.write(bodyBytes)
            output.flush()
        } catch (e: IOException) {
            // Browser closed the connection; the authorization result does not depend on the response page.
        }
    }

    private companion object {
        private const val AUTHORIZE_URL = "https://auth.trakt.tv/oauth/authorize"
        private const val LOOPBACK_ADDRESS = "127.0.0.1"
        private const val UTF_8 = "UTF-8"
        private const val ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"
        private const val CODE_VERIFIER_LENGTH = 64
        private const val STATE_LENGTH = 32
        private const val BACKLOG = 1
        private const val TIMEOUT_MILLIS = 5L * 60L * 1000L
        private const val NANOS_IN_MILLI = 1_000_000L
        private const val CLIENT_TIMEOUT_MILLIS = 5_000
        private const val MAX_HEADER_LINES = 100
        private const val MAX_ERROR_LENGTH = 64
        private const val SUCCESS_PAGE =
            "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>Movies</title></head>" +
                "<body><h3>Signed in to Trakt</h3><p>You can close this tab and return to the app.</p></body></html>"
        private const val FAILURE_PAGE =
            "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>Movies</title></head>" +
                "<body><h3>Trakt sign in was cancelled or failed</h3><p>You can close this tab and return to the app.</p></body></html>"
    }
}
