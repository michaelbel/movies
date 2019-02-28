package org.michaelbel.moviemade.presentation.features.account

import android.content.SharedPreferences
import org.michaelbel.moviemade.core.entity.SessionId
import org.michaelbel.moviemade.core.entity.Username
import org.michaelbel.moviemade.core.utils.*
import org.michaelbel.moviemade.presentation.base.Presenter
import retrofit2.HttpException

class AccountPresenter internal constructor(
    private val view: AccountContract.View,
    private val repository: AccountContract.Repository,
    private val preferences: SharedPreferences
): Presenter(), AccountContract.Presenter {

    override fun attach(view: AccountContract.View) {}

    override fun createSessionId(token: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.createSessionId(token)
            .subscribe({
                if (it.success) {
                    preferences.edit().putString(KEY_SESSION_ID, it.sessionId).apply()
                    view.sessionChanged(true)
                }
            }, { view.setError(Error.ERR_NO_CONNECTION) }))
    }

    override fun authWithLogin(un: Username) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.authWithLogin(un)
            .subscribe({
                if (it != null) {
                    if (it.success) {
                        val authorizedToken = it.requestToken
                        createSessionId(authorizedToken)
                    }
                }
            }, { view.setError(Error.ERROR_AUTH_WITH_LOGIN) }))
    }

    override fun deleteSession() {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        disposable.add(repository.deleteSession(SessionId(sessionId))
            .subscribe({
                if (it != null) {
                    if (it.success) {
                        view.sessionChanged(false)
                    }
                }
            }, { view.setError(Error.ERR_NO_CONNECTION) }))
    }

    override fun getAccountDetails() {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        val sessionId = preferences.getString(KEY_SESSION_ID, "") ?: ""
        disposable.add(repository.getAccountDetails(sessionId)
            .subscribe({
                if (it != null) {
                    view.setAccount(it)
                }
            }, {
                val code = (it as HttpException).code()
                if (code == 401) {
                    view.setError(Error.ERROR_UNAUTHORIZED)
                } else if (code == 404) {
                    view.setError(Error.ERROR_NOT_FOUND)
                }
            }))
    }

    override fun createRequestToken() {
        if (NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposable.add(repository.createRequestToken()
            .subscribe({
                if (it != null) {
                    if (it.success) {
                        preferences.edit().putString(KEY_TOKEN, it.requestToken).apply()
                        preferences.edit().putString(KEY_DATE_AUTHORISED, it.date).apply()
                        view.startBrowserAuth(it.requestToken)
                    }
                }
            }, { view.setError(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    override fun createRequestToken(name: String, pass: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposable.add(repository.createRequestToken(name, pass)
            .subscribe({
                if (it != null) {
                    if (it.success) {
                        preferences.edit().putString(KEY_TOKEN, it.requestToken).apply()
                        preferences.edit().putString(KEY_DATE_AUTHORISED, it.date).apply()
                        val username = Username(name, pass, it.requestToken)
                        authWithLogin(username)
                    }
                }
            }, { view.setError(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}