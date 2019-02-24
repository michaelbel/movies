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
    private val sharedPreferences: SharedPreferences
): Presenter(), AccountContract.Presenter {

    override fun attach(view: AccountContract.View) {}

    override fun createSessionId(token: String) {
        if (!NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.createSessionId(token)
            .subscribe({ session ->
                if (session != null) {
                    if (session.success) {
                        sharedPreferences.edit().putString(KEY_SESSION_ID, session.sessionId).apply()
                        view.sessionChanged(true)
                    }
                }
            }, { view.setError(Error.ERR_NO_CONNECTION) }))
    }

    override fun authWithLogin(un: Username) {
        if (!NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.authWithLogin(un)
            .subscribe({ tokenResponse ->
                if (tokenResponse != null) {
                    if (tokenResponse.success) {
                        val authorizedToken = tokenResponse.requestToken
                        createSessionId(authorizedToken)
                    }
                }
            }, { view.setError(Error.ERROR_AUTH_WITH_LOGIN) }))
    }

    override fun deleteSession() {
        if (!NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.deleteSession(SessionId(sharedPreferences.getString(KEY_SESSION_ID, "")!!))
            .subscribe({ deletedSession ->
                if (deletedSession != null) {
                    if (deletedSession.success) {
                        view.sessionChanged(false)
                    }
                }
            }, { view.setError(Error.ERR_NO_CONNECTION) }))
    }

    override fun getAccountDetails() {
        if (!NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.getAccountDetails(sharedPreferences.getString(KEY_SESSION_ID, "")!!)
            .subscribe({ account ->
                if (account != null) {
                    view.setAccount(account)
                }
            }, { e ->
                val code = (e as HttpException).code()
                if (code == 401) {
                    view.setError(Error.ERROR_UNAUTHORIZED)
                } else if (code == 404) {
                    view.setError(Error.ERROR_NOT_FOUND)
                }
            }))
    }

    override fun createRequestToken() {
        if (!NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposable.add(repository.createRequestToken()
            .subscribe({ response ->
                if (response != null) {
                    if (response.success) {
                        sharedPreferences.edit().putString(KEY_TOKEN, response.requestToken).apply()
                        sharedPreferences.edit().putString(KEY_DATE_AUTHORISED, response.date).apply()
                        view.startBrowserAuth(response.requestToken)
                    }
                }
            }, { view.setError(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    override fun createRequestToken(name: String, pass: String) {
        if (!NetworkUtil.isNetworkConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposable.add(repository.createRequestToken(name, pass)
            .subscribe({ response ->
                if (response != null) {
                    if (response.success) {
                        sharedPreferences.edit().putString(KEY_TOKEN, response.requestToken).apply()
                        sharedPreferences.edit().putString(KEY_DATE_AUTHORISED, response.date).apply()
                        val username = Username(name, pass, response.requestToken)
                        authWithLogin(username)
                    }
                }
            }, { view.setError(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}