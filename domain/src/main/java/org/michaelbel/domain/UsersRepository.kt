package org.michaelbel.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.data.Video
import org.michaelbel.data.local.dao.UsersDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.*

class UsersRepository(private val api: Api, private val dao: UsersDao) {

    fun createSessionId(apiKey: String, token: String): Observable<String> {
        return api.createSession(apiKey, RequestToken(token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { if (it.success) it.sessionId else "" }
    }

    fun authWithLogin(apiKey: String, un: Username): Observable<String> {
        return api.createSessionWithLogin(apiKey, un)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { if (it.success) it.requestToken else "" }
    }

    fun deleteSession(apiKey: String, sessionId: SessionId): Observable<Boolean> {
        return api.deleteSession(apiKey, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.success }
    }

    fun getAccountDetails(apiKey: String, sessionId: String): Observable<Account> {
        return api.getDetails(apiKey, sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createRequestToken(apiKey: String): Observable<Token> {
        return api.createRequestToken(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun createRequestToken(apiKey: String, name: String, pass: String): Observable<Token> {
        return api.createRequestToken(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun add(movieId: Int, items: List<Video>) {

    }
}