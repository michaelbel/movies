package org.michaelbel.moviemade.presentation.features.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.data.remote.model.Account
import org.michaelbel.data.remote.model.SessionId
import org.michaelbel.data.remote.model.Token
import org.michaelbel.data.remote.model.Username
import org.michaelbel.domain.UsersRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.errors.Error

class LoginModel(val repository: UsersRepository): ViewModel() {

    private val disposable = CompositeDisposable()

    var sessionCreated = MutableLiveData<String>()
    var sessionDeleted = MutableLiveData<Any>()
    var error = MutableLiveData<Int>()
    var throwable = MutableLiveData<Throwable>()
    var account = MutableLiveData<Account>()
    var browserAuth = MutableLiveData<String>()
    var token = MutableLiveData<Token>()

    fun createSessionId(token: String) {
        disposable.add(repository.createSessionId(TMDB_API_KEY, token)
                .subscribe({ sessionCreated.postValue(it) }, { error.postValue(Error.ERR_NO_CONNECTION) }))
    }

    fun deleteSession(sessionId: String) {
        disposable.add(repository.deleteSession(TMDB_API_KEY, SessionId(sessionId))
                .subscribe({
                    if (it) {
                        sessionDeleted.postValue(0)
                    }
                }, { error.postValue(Error.ERR_NO_CONNECTION) }))
    }

    fun accountDetails(sessionId: String) {
        disposable.add(repository.getAccountDetails(TMDB_API_KEY, sessionId)
                .subscribe({ account.postValue(it) }, { throwable.postValue(it) }))
    }

    fun createRequestToken() {
        disposable.add(repository.createRequestToken(TMDB_API_KEY)
                .subscribe({
                    if (it.success) {
                        token.postValue(it)
                        browserAuth.postValue(it.requestToken)
                    }
                }, { error.postValue(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    fun createRequestToken(name: String, pass: String) {
        disposable.add(repository.createRequestToken(TMDB_API_KEY, name, pass)
                .subscribe({
                    if (it.success) {
                        token.postValue(it)
                        val username = Username(name, pass, it.requestToken)
                        authWithLogin(username)
                    }
                }, { error.postValue(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    private fun authWithLogin(un: Username) {
        disposable.add(repository.authWithLogin(TMDB_API_KEY, un)
                .subscribe({ createSessionId(it) }, { error.postValue(Error.ERROR_AUTH_WITH_LOGIN) }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}