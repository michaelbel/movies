package org.michaelbel.moviemade.ui.modules.account

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.data.entity.*
import org.michaelbel.moviemade.data.service.AccountService
import org.michaelbel.moviemade.data.service.AuthService

class AccountRepository internal constructor(private val authService: AuthService, private val accountService: AccountService) : AccountContract.Repository {

    override fun createSessionId(token: String): Observable<Session> {
        return authService.createSession(BuildConfig.TMDB_API_KEY, RequestToken(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun authWithLogin(un: Username): Observable<Token> {
        return authService.createSessionWithLogin(BuildConfig.TMDB_API_KEY, un)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun deleteSession(sessionId: SessionId): Observable<DeletedSession> {
        return authService.deleteSession(BuildConfig.TMDB_API_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAccountDetails(sessionId: String): Observable<Account> {
        return accountService.getDetails(BuildConfig.TMDB_API_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createRequestToken(): Observable<Token> {
        return authService.createRequestToken(BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun createRequestToken(name: String, pass: String): Observable<Token> {
        return authService.createRequestToken(BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}