package org.michaelbel.moviemade.ui.modules.account

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.*
import org.michaelbel.moviemade.utils.Error

interface AccountContract {

    interface View {
        fun startBrowserAuth(token: String)
        fun setAccount(account: Account)
        fun sessionChanged(state: Boolean)
        fun setError(@Error error: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun createSessionId(token: String)
        fun authWithLogin(un: Username)
        fun deleteSession()
        fun getAccountDetails()
        fun createRequestToken()
        fun createRequestToken(name: String, pass: String)
        fun onDestroy()
    }

    interface Repository {
        fun createSessionId(token: String) : Observable<Session>
        fun authWithLogin(un: Username) : Observable<Token>
        fun deleteSession(sessionId: SessionId) : Observable<DeletedSession>
        fun getAccountDetails(sessionId: String) : Observable<Account>
        fun createRequestToken() : Observable<Token>
        fun createRequestToken(name: String, pass: String) : Observable<Token>
    }
}