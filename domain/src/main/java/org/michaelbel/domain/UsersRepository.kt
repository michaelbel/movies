package org.michaelbel.domain

import org.michaelbel.data.local.dao.UsersDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Account
import org.michaelbel.data.remote.model.DeletedSession
import org.michaelbel.data.remote.model.RequestToken
import org.michaelbel.data.remote.model.Session
import org.michaelbel.data.remote.model.SessionId
import org.michaelbel.data.remote.model.Token
import org.michaelbel.data.remote.model.Username
import retrofit2.Response

class UsersRepository(private val api: Api, private val dao: UsersDao) {

    //region Remote

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

    //endregion
}