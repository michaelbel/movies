package org.michaelbel.moviemade.presentation.features.account

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.*
import org.michaelbel.moviemade.core.remote.Api

class AccountRepository(private val service: Api): AccountContract.Repository {

    override fun createSessionId(token: String): Observable<String> =
            service.createSession(TMDB_API_KEY, RequestToken(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { if (it.success) it.sessionId else "" }

    override fun authWithLogin(un: Username): Observable<String> =
            service.createSessionWithLogin(TMDB_API_KEY, un)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { if (it.success) it.requestToken else "" }

    override fun deleteSession(sessionId: SessionId): Observable<Boolean> =
            service.deleteSession(TMDB_API_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.success }

    override fun getAccountDetails(sessionId: String): Observable<Account> =
            service.getDetails(TMDB_API_KEY, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun createRequestToken(): Observable<Token> =
            service.createRequestToken(TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    override fun createRequestToken(name: String, pass: String): Observable<Token> =
            service.createRequestToken(TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
}