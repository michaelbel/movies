package org.michaelbel.movies.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateRequestTokenException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: Interactor
): BaseViewModel() {

    var signInLoading by mutableStateOf(false)
    var loginLoading by mutableStateOf(false)
    var error: Throwable? by mutableStateOf(null)
    var requestToken: String? by mutableStateOf(null)

    override fun handleError(throwable: Throwable) {
        signInLoading = false

        when (throwable) {
            is CreateRequestTokenException -> {
                when {
                    throwable.loginViaTmdb -> onResetRequestToken()
                    else -> error = throwable
                }
            }
            is CreateSessionWithLoginException -> error = throwable
            is CreateSessionException -> error = throwable
            is AccountDetailsException -> error = throwable
            else -> super.handleError(throwable)
        }
    }

    fun onSignInClick(username: Username, password: Password, onResult: () -> Unit) = launch {
        error = null
        signInLoading = true
        val token = interactor.createRequestToken(loginViaTmdb = false)
        val sessionToken = interactor.createSessionWithLogin(username, password, token.requestToken)
        interactor.run {
            createSession(sessionToken.requestToken)
            accountDetails()
        }
        onResult()
    }

    fun onLoginClick() = launch {
        error = null
        loginLoading = true
        requestToken = interactor.createRequestToken(loginViaTmdb = true).requestToken
    }

    fun onResetRequestToken() {
        loginLoading = false
        requestToken = null
    }
}