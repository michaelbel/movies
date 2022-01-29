package org.michaelbel.moviemade.app.domain.repo

import org.michaelbel.moviemade.app.data.Api
import org.michaelbel.moviemade.app.data.model.*
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: Api
) {

    suspend fun createSessionId(apiKey: String, token: String): Response<Session> {
        return api.createSession(apiKey, RequestToken(token))
    }

    suspend fun authWithLogin(apiKey: String, un: Username): Response<Token> {
        return api.createSessionWithLogin(apiKey, un)
    }

    suspend fun deleteSession(apiKey: String, sessionId: SessionId): Response<DeletedSession> {
        return api.deleteSession(apiKey, sessionId)
    }

    suspend fun accountDetails(apiKey: String, sessionId: String): Response<Account> {
        return api.details(apiKey, sessionId)
    }

    suspend fun createRequestToken(apiKey: String): Response<Token> {
        return api.createRequestToken(apiKey)
    }

    suspend fun createRequestToken(apiKey: String, name: String, pass: String): Response<Token> {
        return api.createRequestToken(apiKey)
    }
}