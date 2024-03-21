package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.michaelbel.movies.network.model.DeletedSession
import org.michaelbel.movies.network.model.RequestToken
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.SessionRequest
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.network.model.Username
import javax.inject.Inject

internal class KtorAuthenticationService @Inject constructor(
    private val ktorHttpClient: HttpClient
) {

    suspend fun createRequestToken(): Token {
        return ktorHttpClient.get("authentication/token/new?").body()
    }

    suspend fun createSessionWithLogin(
        username: Username
    ): Token {
        return ktorHttpClient.post("authentication/token/validate_with_login?") {
            setBody(username)
        }.body()
    }

    suspend fun createSession(
        authToken: RequestToken
    ): Session {
        return ktorHttpClient.post("authentication/session/new?") {
            setBody(authToken)
        }.body()
    }

    suspend fun deleteSession(
        sessionRequest: SessionRequest
    ): DeletedSession {
        return ktorHttpClient.delete("authentication/session?") {
            setBody(sessionRequest)
        }.body()
    }
}