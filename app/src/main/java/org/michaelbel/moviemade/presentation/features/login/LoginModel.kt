package org.michaelbel.moviemade.presentation.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.remote.model.Account
import org.michaelbel.data.remote.model.Token
import org.michaelbel.data.remote.model.Username
import org.michaelbel.domain.UsersRepository
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.ErrorState.ERR_AUTH_WITH_LOGIN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_CONNECTION_NO_TOKEN
import org.michaelbel.moviemade.core.state.ErrorState.ERR_NO_CONNECTION

class LoginModel(val repository: UsersRepository): ViewModel() {

    var sessionCreated = MutableLiveData<LiveDataEvent<String>>()
    var error = MutableLiveData<LiveDataEvent<Int>>()
    var throwable = MutableLiveData<Throwable>()
    var account = MutableLiveData<Account>()
    var browserAuth = MutableLiveData<LiveDataEvent<String>>()
    var token = MutableLiveData<LiveDataEvent<Token>>()

    fun createSessionId(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.createSessionId(TMDB_API_KEY, token)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val session = result.body()
                        if (session != null) {
                            sessionCreated.postValue(LiveDataEvent(session.sessionId))
                        } else {
                            // todo smth
                        }
                    } else {
                        error.postValue(LiveDataEvent(ERR_NO_CONNECTION))
                    }
                }
            } catch (e: Throwable) {
                error.postValue(LiveDataEvent(ERR_NO_CONNECTION))
            }
        }
    }

    fun createRequestToken() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.createRequestToken(TMDB_API_KEY)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val success = result.body()
                        if (success != null && success.success) {
                            token.postValue(LiveDataEvent(success))
                            browserAuth.postValue(LiveDataEvent(success.requestToken))
                        } else {
                            error.postValue(LiveDataEvent(ERR_CONNECTION_NO_TOKEN))
                        }
                    } else {
                        error.postValue(LiveDataEvent(ERR_CONNECTION_NO_TOKEN))
                    }
                }
            } catch (e: Throwable) {
                error.postValue(LiveDataEvent(ERR_NO_CONNECTION))
            }
        }
    }

    fun createRequestToken(name: String, pass: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.createRequestToken(TMDB_API_KEY, name, pass)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val success = result.body()
                        if (success != null && success.success) {
                            token.postValue(LiveDataEvent(success))

                            val username = Username(name, pass, success.requestToken)
                            authWithLogin(username)
                        } else {
                            error.postValue(LiveDataEvent(ERR_CONNECTION_NO_TOKEN))
                        }
                    } else {
                        error.postValue(LiveDataEvent(ERR_CONNECTION_NO_TOKEN))
                    }
                }
            } catch (e: Throwable) {
                error.postValue(LiveDataEvent(ERR_NO_CONNECTION))
            }
        }
    }

    private fun authWithLogin(un: Username) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.authWithLogin(TMDB_API_KEY, un)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val sessionId = result.body()?.requestToken
                        sessionId?.let { createSessionId(sessionId) }
                    } else {
                        error.postValue(LiveDataEvent(ERR_AUTH_WITH_LOGIN))
                    }
                }
            } catch (e: Throwable) {
                error.postValue(LiveDataEvent(ERR_NO_CONNECTION))
            }
        }
    }
}