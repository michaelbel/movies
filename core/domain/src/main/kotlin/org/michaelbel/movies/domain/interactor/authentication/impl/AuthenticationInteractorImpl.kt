package org.michaelbel.movies.domain.interactor.authentication.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.domain.interactor.authentication.AuthenticationInteractor
import org.michaelbel.movies.domain.repository.authentication.AuthenticationRepository
import org.michaelbel.movies.domain.usecase.DelayUseCase
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.Token

@Singleton
internal class AuthenticationInteractorImpl @Inject constructor(
    @Dispatcher(MoviesDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val authenticationRepository: AuthenticationRepository,
    private val delayUseCase: DelayUseCase
): AuthenticationInteractor {

    override suspend fun createRequestToken(): Token {
        return withContext(dispatcher) { authenticationRepository.createRequestToken() }
    }

    override suspend fun createSessionWithLogin(
        username: String,
        password: String,
        requestToken: String
    ): Token {
        return withContext(dispatcher) {
            authenticationRepository.createSessionWithLogin(username, password, requestToken)
        }
    }

    override suspend fun createSession(token: String): Session {
        return withContext(dispatcher) {
            authenticationRepository.createSession(token)
        }
    }

    override suspend fun deleteSession() {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatcher) {
            authenticationRepository.deleteSession()
        }
    }
}