package org.michaelbel.moviemade.presentation.features.account

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Account
import org.michaelbel.moviemade.core.entity.SessionId
import org.michaelbel.moviemade.core.entity.Token
import org.michaelbel.moviemade.core.entity.Username
import org.michaelbel.moviemade.core.errors.Error
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface AccountContract {

    interface View {
        fun startBrowserAuth(token: String, date: String)
        fun setAccount(account: Account)
        fun sessionCreated(sessionId: String)
        fun sessionDeleted()
        fun setError(@Error error: Int)
        fun setError(throwable: Throwable)
        fun saveToken(token: String, date: String)
    }

    interface Presenter: BasePresenter<View> {
        fun createSessionId(token: String)
        fun authWithLogin(un: Username)
        fun deleteSession(sessionId: String)
        fun getAccountDetails(sessionId: String)
        fun createRequestToken()
        fun createRequestToken(name: String, pass: String)
    }

    interface Repository {
        fun createSessionId(token: String): Observable<String>
        fun authWithLogin(un: Username): Observable<String>
        fun deleteSession(sessionId: SessionId): Observable<Boolean>
        fun getAccountDetails(sessionId: String): Observable<Account>
        fun createRequestToken() : Observable<Token>
        fun createRequestToken(name: String, pass: String): Observable<Token>
    }
}