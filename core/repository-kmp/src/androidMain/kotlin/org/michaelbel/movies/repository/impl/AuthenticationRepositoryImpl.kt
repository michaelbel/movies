package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.common.exceptions.CreateRequestTokenException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.common.exceptions.DeleteSessionException
import org.michaelbel.movies.network.AuthenticationNetworkService
import org.michaelbel.movies.network.model.RequestToken
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.SessionRequest
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.network.model.Username
import org.michaelbel.movies.persistence.database.AccountPersistence
import org.michaelbel.movies.persistence.datastore.MoviesPreferences
import org.michaelbel.movies.repository.AuthenticationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationNetworkService: AuthenticationNetworkService,
    private val accountPersistence: AccountPersistence,
    private val preferences: MoviesPreferences
): AuthenticationRepository {

    override suspend fun createRequestToken(loginViaTmdb: Boolean): Token {
        return try {
            val token = authenticationNetworkService.createRequestToken()
            if (!token.success) {
                throw CreateRequestTokenException(loginViaTmdb)
            }
            token
        } catch (ignored: Exception) {
            throw CreateRequestTokenException(loginViaTmdb)
        }
    }

    override suspend fun createSessionWithLogin(
        username: String,
        password: String,
        requestToken: String
    ): Token {
        return try {
            val token = authenticationNetworkService.createSessionWithLogin(
                username = Username(
                    username = username,
                    password = password,
                    requestToken = requestToken
                )
            )
            if (!token.success) {
                throw CreateSessionWithLoginException
            }
            token
        } catch (ignored: Exception) {
            throw CreateSessionWithLoginException
        }
    }

    override suspend fun createSession(token: String): Session {
        return try {
            val session = authenticationNetworkService.createSession(RequestToken(token))
            if (session.success) {
                preferences.setSessionId(session.sessionId)
            } else {
                throw CreateSessionException
            }
            session
        } catch (ignored: Exception) {
            throw CreateSessionException
        }
    }

    override suspend fun deleteSession() {
        runCatching {
            val sessionId = preferences.sessionId().orEmpty()
            val deletedSession = authenticationNetworkService.deleteSession(SessionRequest(sessionId))
            if (deletedSession.success) {
                val accountId = preferences.accountId()
                accountPersistence.removeById(accountId)
                preferences.run {
                    removeSessionId()
                    removeAccountId()
                }
            } else {
                throw DeleteSessionException
            }
        }.onFailure {
            throw DeleteSessionException
        }
    }
}