package org.michaelbel.moviemade.presentation.features.account

import org.michaelbel.moviemade.core.Error
import org.michaelbel.moviemade.core.entity.SessionId
import org.michaelbel.moviemade.core.entity.Username
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter

class AccountPresenter (
    private val repository: AccountContract.Repository
): Presenter(), AccountContract.Presenter {

    private lateinit var view: AccountContract.View

    override fun attach(view: AccountContract.View) {
        this.view = view
    }

    override fun createSessionId(token: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.createSessionId(token)
            .subscribe({ view.sessionCreated(it) }, { view.setError(Error.ERR_NO_CONNECTION) }))
    }

    override fun authWithLogin(un: Username) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.authWithLogin(un)
            .subscribe({ createSessionId(it) }, { view.setError(Error.ERROR_AUTH_WITH_LOGIN) }))
    }

    override fun deleteSession(sessionId: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.deleteSession(SessionId(sessionId))
            .subscribe({
                if (it) {
                    view.sessionDeleted()
                }
            }, { view.setError(Error.ERR_NO_CONNECTION) }))
    }

    override fun getAccountDetails(sessionId: String) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERR_NO_CONNECTION)
            return
        }

        disposable.add(repository.getAccountDetails(sessionId)
            .subscribe({ view.setAccount(it) }, { view.setError(it) }))
    }

    override fun createRequestToken() {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.setError(Error.ERROR_CONNECTION_NO_TOKEN)
            return
        }

        disposable.add(repository.createRequestToken()
            .subscribe({
                if (it.success) {
                    view.startBrowserAuth(it.requestToken, it.date)
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
                if (it.success) {
                    view.saveToken(it.requestToken, it.date)
                    val username = Username(name, pass, it.requestToken)
                    authWithLogin(username)
                }
            }, { view.setError(Error.ERROR_CONNECTION_NO_TOKEN) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}