package org.michaelbel.movies.main.navigation

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor

class MainNavViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor
): BaseViewModel() {

    private val requestToken: String? = savedStateHandle["requestToken"]
    private val approved: String? = savedStateHandle["approved"]

    private val _snackbarMessage = Channel<String>()
    val snackbarMessage: Flow<String> = _snackbarMessage.receiveAsFlow()

    init {
        authorizeAccount(requestToken, approved.toBoolean())
    }

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            is CreateSessionException -> {
                scope.launch { _snackbarMessage.send("Failure while signing in. Wrong token or no approval") }
            }
            is AccountDetailsException -> {
                scope.launch { _snackbarMessage.send("Failure while signing in. Wrong token or no approval") }
            }
            else -> super.handleError(throwable)
        }
    }

    private fun authorizeAccount(requestToken: String?, approved: Boolean?) {
        if (requestToken == null || approved == null) return
        scope.launch {
            interactor.run {
                createSession(requestToken)
                accountDetails()
                _snackbarMessage.send("Successful authorization")
            }
        }
    }
}