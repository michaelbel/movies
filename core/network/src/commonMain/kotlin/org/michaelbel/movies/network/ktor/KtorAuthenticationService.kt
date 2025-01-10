package org.michaelbel.movies.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.michaelbel.movies.network.config.isNeedApiKeyQuery
import org.michaelbel.movies.network.config.tmdbApiKey
import org.michaelbel.movies.network.model.DeletedSession
import org.michaelbel.movies.network.model.RequestToken
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.SessionRequest
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.network.model.Username

internal class KtorAuthenticationService(
    private val ktorHttpClient: HttpClient
) {

    suspend fun createRequestToken(): Token {
        return ktorHttpClient.get("authentication/token/new?") {
            if (isNeedApiKeyQuery) {
                parameter("api_key", tmdbApiKey)
            }
        }.body()
    }

    suspend fun createSessionWithLogin(
        username: Username
    ): Token {
        return ktorHttpClient.post("authentication/token/validate_with_login?") {
            if (isNeedApiKeyQuery) {
                parameter("api_key", tmdbApiKey)
            }
            setBody(username)
        }.body()
    }

    suspend fun createSession(
        authToken: RequestToken
    ): Session {
        return ktorHttpClient.post("authentication/session/new?") {
            if (isNeedApiKeyQuery) {
                parameter("api_key", tmdbApiKey)
            }
            setBody(authToken)
        }.body()
    }

    suspend fun deleteSession(
        sessionRequest: SessionRequest
    ): DeletedSession {
        return ktorHttpClient.delete("authentication/session?") {
            if (isNeedApiKeyQuery) {
                parameter("api_key", tmdbApiKey)
            }
            setBody(sessionRequest)
        }.body()
    }
}