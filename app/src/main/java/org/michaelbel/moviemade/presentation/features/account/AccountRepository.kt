package org.michaelbel.moviemade.presentation.features.account

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.*
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.AuthService

class AccountRepository internal constructor(
        private val authService: AuthService,
        private val accountService: AccountService
): AccountContract.Repository {

    override fun createSessionId(token: String): Observable<Session> {
        return authService.createSession(TMDB_API_KEY, RequestToken(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun authWithLogin(un: Username): Observable<Token> {
        return authService.createSessionWithLogin(TMDB_API_KEY, un)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteSession(sessionId: SessionId): Observable<DeletedSession> {
        return authService.deleteSession(TMDB_API_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAccountDetails(sessionId: String): Observable<Account> {
        return accountService.getDetails(TMDB_API_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createRequestToken(): Observable<Token> {
        return authService.createRequestToken(TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createRequestToken(name: String, pass: String): Observable<Token> {
        return authService.createRequestToken(TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}