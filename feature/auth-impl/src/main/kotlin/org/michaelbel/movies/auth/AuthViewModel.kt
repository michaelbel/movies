package org.michaelbel.movies.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateRequestTokenException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.exceptions.CreateSessionWithLoginException
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.model.Token
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val interactor: Interactor
): BaseViewModel() {

    var loading: Boolean by mutableStateOf(false)
    var error: Throwable? by mutableStateOf(null)

    override fun handleError(throwable: Throwable) {
        loading = false

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
        loading = true

        val token: Token = interactor.createRequestToken()
        val sessionToken: Token = interactor.createSessionWithLogin(
            username,
            password,
            token.requestToken
        )
        interactor.createSession(sessionToken.requestToken)
        interactor.accountDetails()

        onResult()
    }
}