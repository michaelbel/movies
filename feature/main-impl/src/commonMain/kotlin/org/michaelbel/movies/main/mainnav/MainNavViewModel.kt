package org.michaelbel.movies.main.mainnav

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.main.intent.MainIntent
import org.michaelbel.movies.main.model.MainModel

class MainNavViewModel(
    private val interactor: Interactor
): MoviesViewModel<MainModel, MainIntent>(MainModel()) {

    private val _snackbarMessage = Channel<String>()
    val snackbarMessage: Flow<String> = _snackbarMessage.receiveAsFlow()

    override fun dispatch(intent: MainIntent) {}

    override fun catch(throwable: Throwable) {
        when (throwable) {
            is CreateSessionException -> launch { _snackbarMessage.send("Failure while signing in. Wrong token or no approval") }
            is AccountDetailsException -> launch { _snackbarMessage.send("Failure while signing in. Wrong token or no approval") }
            else -> super.catch(throwable)
        }
    }

    fun onRedirect(requestToken: String?, approved: Boolean?) {
        if (requestToken == null || approved == null) return
        authorizeAccount(requestToken, approved)
    }

    private fun authorizeAccount(requestToken: String, approved: Boolean) {
        launch {
            interactor.run {
                createSession(requestToken)
                accountDetails()
                _snackbarMessage.send("Successful authorization")
            }
        }
    }
}