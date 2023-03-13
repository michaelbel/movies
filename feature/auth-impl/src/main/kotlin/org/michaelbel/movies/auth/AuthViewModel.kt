package org.michaelbel.movies.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.exceptions.AccountDetailsException
import org.michaelbel.movies.domain.exceptions.CreateRequestTokenException
import org.michaelbel.movies.domain.exceptions.CreateSessionException
import org.michaelbel.movies.domain.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.interactor.authentication.AuthenticationInteractor
import org.michaelbel.movies.network.model.Token
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    private val accountInteractor: AccountInteractor
): BaseViewModel() {

    private val _error: MutableStateFlow<Throwable?> = MutableStateFlow(null)
    val error: StateFlow<Throwable?> = _error.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    override fun handleError(throwable: Throwable) {
        _loading.value = false

        when (throwable) {
            is CreateRequestTokenException -> {
                _error.value = CreateRequestTokenException
            }
            is CreateSessionWithLoginException -> {
                _error.value = CreateSessionWithLoginException
            }
            is CreateSessionException -> {
                _error.value = CreateSessionException
            }
            is AccountDetailsException -> {
                _error.value = AccountDetailsException
            }
            else -> super.handleError(throwable)
        }
    }

    fun onSignInClick(username: String, password: String, onResult: () -> Unit) = launch {
        _error.value = null
        _loading.value = true

        val token: Token = authenticationInteractor.createRequestToken()
        val sessionToken: Token = authenticationInteractor.createSessionWithLogin(
            username,
            password,
            token.requestToken
        )
        authenticationInteractor.createSession(sessionToken.requestToken)
        accountInteractor.accountDetails()

        onResult()
    }
}