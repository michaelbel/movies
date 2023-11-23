package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.AuthenticationInteractor
import org.michaelbel.movies.interactor.usecase.DelayUseCase
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.repository.AuthenticationRepository

@Singleton
internal class AuthenticationInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val authenticationRepository: AuthenticationRepository,
    private val delayUseCase: DelayUseCase
): AuthenticationInteractor {

    override suspend fun createRequestToken(): Token {
        return withContext(dispatchers.io) { authenticationRepository.createRequestToken() }
    }

    override suspend fun createSessionWithLogin(
        username: String,
        password: String,
        requestToken: String
    ): Token {
        return withContext(dispatchers.io) {
            authenticationRepository.createSessionWithLogin(username, password, requestToken)
        }
    }

    override suspend fun createSession(token: String): Session {
        return withContext(dispatchers.io) {
            authenticationRepository.createSession(token)
        }
    }

    override suspend fun deleteSession() {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatchers.io) {
            authenticationRepository.deleteSession()
        }
    }
}