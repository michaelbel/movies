package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.AuthenticationInteractor
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.repository.AuthenticationRepository

@Singleton
internal class AuthenticationInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val authenticationRepository: AuthenticationRepository
): AuthenticationInteractor {

    override suspend fun createRequestToken(loginViaTmdb: Boolean): Token {
        return withContext(dispatchers.io) { authenticationRepository.createRequestToken(loginViaTmdb) }
    }

    override suspend fun createSessionWithLogin(
        username: Username,
        password: Password,
        requestToken: String
    ): Token {
        return withContext(dispatchers.io) {
            authenticationRepository.createSessionWithLogin(username.value, password.value, requestToken)
        }
    }

    override suspend fun createSession(token: String): Session {
        return withContext(dispatchers.io) { authenticationRepository.createSession(token) }
    }

    override suspend fun deleteSession() {
        return withContext(dispatchers.io) { authenticationRepository.deleteSession() }
    }
}