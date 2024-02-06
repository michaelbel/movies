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

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: Interactor
): BaseViewModel() {

    var signInLoading: Boolean by mutableStateOf(false)
    var loginLoading: Boolean by mutableStateOf(false)
    var error: Throwable? by mutableStateOf(null)
    var requestToken: String? by mutableStateOf(null)

    override fun handleError(throwable: Throwable) {
        signInLoading = false

        when (throwable) {
            is CreateRequestTokenException -> {
                error = CreateRequestTokenException
            }
            is CreateSessionWithLoginException -> {
                error = CreateSessionWithLoginException
            }
            is CreateSessionException -> {
                error = CreateSessionException
            }
            is AccountDetailsException -> {
                error = AccountDetailsException
            }
            else -> super.handleError(throwable)
        }
    }

    fun onSignInClick(username: String, password: String, onResult: () -> Unit) = launch {
        error = null
        signInLoading = true
        val token = interactor.createRequestToken()
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
        requestToken = interactor.createRequestToken().requestToken
    }

    fun onResetRequestToken() {
        loginLoading = false
        requestToken = null
    }
}