package org.michaelbel.movies.domain.repository.authentication.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import org.michaelbel.movies.domain.data.dao.AccountDao
import org.michaelbel.movies.domain.exceptions.CreateRequestTokenException
import org.michaelbel.movies.domain.exceptions.CreateSessionException
import org.michaelbel.movies.domain.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.domain.exceptions.DeleteSessionException
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_ACCOUNT_ID_KEY
import org.michaelbel.movies.domain.preferences.constants.PREFERENCE_SESSION_ID_KEY
import org.michaelbel.movies.domain.repository.authentication.AuthenticationRepository
import org.michaelbel.movies.domain.service.authentication.AuthenticationService
import org.michaelbel.movies.entities.tmdbApiKey
import org.michaelbel.movies.network.model.DeletedSession
import org.michaelbel.movies.network.model.RequestToken
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.SessionRequest
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.network.model.Username
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AuthenticationRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val accountDao: AccountDao,
    private val dataStore: DataStore<Preferences>
): AuthenticationRepository {

    override suspend fun createRequestToken(): Token {
        return try {
            val token: Token = authenticationService.createRequestToken(tmdbApiKey)
            if (!token.success) {
                throw CreateRequestTokenException
            }
            token
        } catch (e: Exception) {
            throw CreateRequestTokenException
        }
    }

    override suspend fun createSessionWithLogin(
        username: String,
        password: String,
        requestToken: String
    ): Token {
        return try {
            val token: Token = authenticationService.createSessionWithLogin(
                apiKey = tmdbApiKey,
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
        } catch (e: Exception) {
            throw CreateSessionWithLoginException
        }
    }

    override suspend fun createSession(token: String): Session {
        return try {
            val session: Session = authenticationService.createSession(
                apiKey = tmdbApiKey,
                authToken = RequestToken(token)
            )
            if (session.success) {
                dataStore.edit { preferences ->
                    preferences[PREFERENCE_SESSION_ID_KEY] = session.sessionId
                }
            } else {
                throw CreateSessionException
            }
            session
        } catch (e: Exception) {
            throw CreateSessionException
        }
    }

    override suspend fun deleteSession() {
        try {
            val sessionId: String = dataStore.data.first()[PREFERENCE_SESSION_ID_KEY].orEmpty()
            val sessionRequest = SessionRequest(sessionId)
            val deletedSession: DeletedSession = authenticationService.deleteSession(
                apiKey = tmdbApiKey,
                sessionRequest = sessionRequest
            )
            if (deletedSession.success) {
                val accountId: Int = dataStore.data.first()[PREFERENCE_ACCOUNT_ID_KEY] ?: 0
                accountDao.removeById(accountId)

                dataStore.edit { preferences ->
                    preferences.remove(PREFERENCE_SESSION_ID_KEY)
                    preferences.remove(PREFERENCE_ACCOUNT_ID_KEY)
                }
            } else {
                throw DeleteSessionException
            }
        } catch (e: Exception) {
            throw DeleteSessionException
        }
    }
}