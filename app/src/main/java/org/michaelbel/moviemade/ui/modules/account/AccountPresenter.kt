package org.michaelbel.moviemade.ui.modules.account

import android.content.SharedPreferences
import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.data.constants.CODE_401
import org.michaelbel.moviemade.data.constants.CODE_404
import org.michaelbel.moviemade.data.entity.SessionId
import org.michaelbel.moviemade.data.entity.Username
import org.michaelbel.moviemade.utils.*
import retrofit2.HttpException

class AccountPresenter internal constructor(
        private val view: AccountContract.View,
        private val repository: AccountContract.Repository,
        private val sharedPreferences: SharedPreferences) : AccountContract.Presenter {

    override fun setView(view: AccountContract.View) {}

    private val disposables = CompositeDisposable()

    override fun createSessionId(token: String) {
        // Fixme.
        if (NetworkUtil.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION)
            return
        }

        disposables.add(repository.createSessionId(token)
            .subscribe({ session ->
                if (session != null) {
                    if (session.success) {
                        sharedPreferences.edit().putString(KEY_SESSION_ID, session.sessionId).apply()
                        view.sessionChanged(true)
                    }
                }
            }, { view.setError(Error.ERROR_NO_CONNECTION) }))
    }

    override fun authWithLogin(un: Username) {
        // Fixme.
        if (NetworkUtil.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION)
            return
        }

        disposables.add(repository.authWithLogin(un)
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
        // Fixme.
        if (NetworkUtil.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION)
            return
        }

        disposables.add(repository.deleteSession(SessionId(sharedPreferences.getString(KEY_SESSION_ID, "")!!))
            .subscribe({ deletedSession ->
                if (deletedSession != null) {
                    if (deletedSession.success) {
                        view.sessionChanged(false)
                    }
                }
            }, { view.setError(Error.ERROR_NO_CONNECTION) }))
    }

    override fun getAccountDetails() {
        // Fixme.
        if (NetworkUtil.notConnected()) {
            view.setError(Error.ERROR_NO_CONNECTION)
            return
        }

        disposables.add(repository.getAccountDetails(sharedPreferences.getString(KEY_SESSION_ID, "")!!)
            .subscribe({ account ->
                if (account != null) {
                    view.setAccount(account)
                }
            }, { e ->
                val code = (e as HttpException).code()
                if (code == CODE_401) {
                    view.setError(Error.ERROR_UNAUTHORIZED)
                } else if (code == CODE_404) {
                    view.setError(Error.ERROR_NOT_FOUND)
                }
            }))
    }

    override fun createRequestToken() {
        // Fixme.
        if (NetworkUtil.notConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposables.add(repository.createRequestToken()
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
        // Fixme.
        if (NetworkUtil.notConnected()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposables.add(repository.createRequestToken(name, pass)
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

    override fun onDestroy() {
        disposables.dispose()
    }
}