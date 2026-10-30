package org.michaelbel.movies.auth

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.michaelbel.movies.auth.intent.AuthIntent
import org.michaelbel.movies.auth.model.AuthModel
import org.michaelbel.movies.common.mvi.Event
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase
import org.michaelbel.movies.domain.usecase.AccountDetailsUseCase.AccountDetailsException
import org.michaelbel.movies.domain.usecase.CreateRequestTokenUseCase
import org.michaelbel.movies.domain.usecase.CreateRequestTokenUseCase.CreateRequestTokenException
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionUseCase.CreateSessionException
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase
import org.michaelbel.movies.domain.usecase.CreateSessionWithLoginUseCase.CreateSessionWithLoginException
import org.michaelbel.movies.domain.usecase.ProviderFlowUseCase
import org.michaelbel.movies.domain.usecase.TraktSignInUseCase
import org.michaelbel.movies.domain.usecase.TraktSignInUseCase.TraktSignInException
import org.michaelbel.movies.interactor.entity.AppProvider
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.ui.pending.PendingActionStore

class AuthViewModel(
    private val accountDetailsUseCase: AccountDetailsUseCase,
    private val createSessionUseCase: CreateSessionUseCase,
    private val createRequestTokenUseCase: CreateRequestTokenUseCase,
    private val createSessionWithLoginUseCase: CreateSessionWithLoginUseCase,
    private val providerFlowUseCase: ProviderFlowUseCase,
    private val traktSignInUseCase: TraktSignInUseCase
): MoviesViewModel<AuthModel, AuthIntent, Event>(AuthModel()) {

    init {
        dispatch(AuthIntent.CollectProvider)
    }

    override fun dispatch(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.CollectProvider -> {
                launch {
                    providerFlowUseCase(Unit).map(AppProvider::transform).distinctUntilChanged().collectLatest { provider ->
                        reduce { it.copy(provider = provider) }
                    }
                }
            }
            is AuthIntent.BackClick -> {
                stateFlow.value.traktSignInJob?.cancel()
                PendingActionStore.clear()
                launch { MainNavigator.back() }
            }
            is AuthIntent.LoginClick -> {
                val job = launch {
                    reduce { it.copy(error = null) }
                    val requestToken = createRequestTokenUseCase(true).getOrThrow().requestToken
                    reduce { it.copy(requestToken = requestToken) }
                }
                reduce { it.copy(loginJob = job) }
            }
            is AuthIntent.ResetRequestToken -> {
                stateFlow.value.loginJob?.cancel()
                reduce { it.copy(requestToken = null, loginJob = null) }
            }
            is AuthIntent.UsernameChange -> reduce { it.copy(username = intent.username) }
            is AuthIntent.PasswordChange -> reduce { it.copy(password = intent.password) }
            is AuthIntent.SignInClick -> {
                reduce { it.copy(error = null) }
                val job = launch {
                    val token = createRequestTokenUseCase(false).getOrThrow()
                    val params = CreateSessionWithLoginUseCase.Params(
                        username = intent.username.value,
                        password = intent.password.value,
                        requestToken = token.requestToken
                    )
                    val sessionToken = createSessionWithLoginUseCase(params).getOrThrow()
                    createSessionUseCase(sessionToken.requestToken).getOrThrow()
                    accountDetailsUseCase(Unit).getOrThrow()
                    MainNavigator.back()
                }
                reduce { it.copy(signInJob = job) }
            }
            is AuthIntent.TraktSignInClick -> {
                val job = launch {
                    reduce { it.copy(error = null) }
                    traktSignInUseCase(Unit).getOrThrow()
                    MainNavigator.back()
                }
                reduce { it.copy(traktSignInJob = job) }
            }
            is AuthIntent.TraktSignInCancelClick -> {
                stateFlow.value.traktSignInJob?.cancel()
                reduce { it.copy(traktSignInJob = null) }
            }
        }
    }

    override fun catch(throwable: Throwable) {
        when (throwable) {
            is CreateRequestTokenException -> {
                when {
                    throwable.loginViaTmdb -> dispatch(AuthIntent.ResetRequestToken)
                    else -> {
                        stateFlow.value.signInJob?.cancel()
                        reduce { it.copy(requestToken = null, signInJob = null, error = throwable) }
                    }
                }
            }
            is CreateSessionWithLoginException -> {
                stateFlow.value.signInJob?.cancel()
                reduce { it.copy(requestToken = null, signInJob = null, error = throwable) }
            }
            is CreateSessionException -> {
                stateFlow.value.signInJob?.cancel()
                reduce { it.copy(requestToken = null, signInJob = null, error = throwable) }
            }
            is AccountDetailsException -> {
                stateFlow.value.signInJob?.cancel()
                reduce { it.copy(requestToken = null, signInJob = null, error = throwable) }
            }
            is TraktSignInException -> {
                reduce { it.copy(error = throwable, traktSignInJob = null) }
            }
            else -> super.catch(throwable)
        }
    }
}
