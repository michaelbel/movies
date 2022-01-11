package org.michaelbel.moviemade.presentation.features.login

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.remote.model.Account
import org.michaelbel.data.remote.model.Token
import org.michaelbel.data.remote.model.Username
import org.michaelbel.domain.repository.AuthRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.local.SharedPrefs
import org.michaelbel.moviemade.core.state.ErrorState.ERR_AUTH_WITH_LOGIN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_CONNECTION_NO_TOKEN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_NO_CONNECTION
import javax.inject.Inject

@HiltViewModel
class LoginModel @Inject constructor(
    val preferences: SharedPreferences,
    private val repository: AuthRepository
): ViewModel() {

    var sessionCreated = MutableSharedFlow<String>()
    var error = MutableSharedFlow<Int>()
    var throwable = MutableSharedFlow<Throwable>()
    var account = MutableSharedFlow<Account>()
    var browserAuth = MutableSharedFlow<String>()

    fun createSessionId(token: String) {
        viewModelScope.launch {
            try {
                val result = repository.createSessionId(TMDB_API_KEY, token)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val session = result.body()
                        if (session != null) {
                            sessionCreated.emit(session.sessionId)
                        } else {
                            // todo smth
                        }
                    } else {
                        error.emit(ERR_NO_CONNECTION)
                    }
                }
            } catch (e: Throwable) {
                error.emit(ERR_NO_CONNECTION)
            }
        }
    }

    fun createRequestToken() {
        viewModelScope.launch {
            try {
                val result = repository.createRequestToken(TMDB_API_KEY)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val success = result.body()
                        if (success != null && success.success) {
                            saveToken(success)
                            browserAuth.emit(success.requestToken)
                        } else {
                            error.emit(ERR_CONNECTION_NO_TOKEN)
                        }
                    } else {
                        error.emit(ERR_CONNECTION_NO_TOKEN)
                    }
                }
            } catch (e: Throwable) {
                error.emit(ERR_NO_CONNECTION)
            }
        }
    }

    fun createRequestToken(name: String, pass: String) {
        viewModelScope.launch {
            try {
                val result = repository.createRequestToken(TMDB_API_KEY, name, pass)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val success = result.body()
                        if (success != null && success.success) {
                            saveToken(success)

                            val username = Username(name, pass, success.requestToken)
                            authWithLogin(username)
                        } else {
                            error.emit(ERR_CONNECTION_NO_TOKEN)
                        }
                    } else {
                        error.emit(ERR_CONNECTION_NO_TOKEN)
                    }
                }
            } catch (e: Throwable) {
                error.emit(ERR_NO_CONNECTION)
            }
        }
    }

    private fun authWithLogin(un: Username) {
        viewModelScope.launch {
            try {
                val result = repository.authWithLogin(TMDB_API_KEY, un)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val sessionId = result.body()?.requestToken
                        sessionId?.let { createSessionId(sessionId) }
                    } else {
                        error.emit(ERR_AUTH_WITH_LOGIN)
                    }
                }
            } catch (e: Throwable) {
                error.emit(ERR_NO_CONNECTION)
            }
        }
    }

    private fun saveToken(token: Token) {
        preferences.edit {
            putString(SharedPrefs.KEY_TOKEN, token.requestToken)
            putString(SharedPrefs.KEY_DATE_AUTHORISED, token.date)
        }
    }
}