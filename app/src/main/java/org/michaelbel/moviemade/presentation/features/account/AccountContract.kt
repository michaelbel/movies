package org.michaelbel.moviemade.presentation.features.account

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.*
import org.michaelbel.moviemade.core.utils.Error
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface AccountContract {

    interface View {
        fun startBrowserAuth(token: String)
        fun setAccount(account: Account)
        fun sessionChanged(state: Boolean)
        fun setError(@Error error: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun createSessionId(token: String)
        fun authWithLogin(un: Username)
        fun deleteSession()
        fun getAccountDetails()
        fun createRequestToken()
        fun createRequestToken(name: String, pass: String)
    }

    interface Repository {
        fun createSessionId(token: String): Observable<Session>
        fun authWithLogin(un: Username): Observable<Token>
        fun deleteSession(sessionId: SessionId): Observable<DeletedSession>
        fun getAccountDetails(sessionId: String): Observable<Account>
        fun createRequestToken() : Observable<Token>
        fun createRequestToken(name: String, pass: String): Observable<Token>
    }
}