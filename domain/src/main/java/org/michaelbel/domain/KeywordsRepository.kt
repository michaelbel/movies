package org.michaelbel.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.data.Keyword
import org.michaelbel.data.local.dao.KeywordsDao
import org.michaelbel.data.remote.Api
import java.util.*

class KeywordsRepository(private val api: Api, private val dao: KeywordsDao) {

    fun keywords(movieId: Int, apiKey: String): Observable<List<Keyword>> {
        return api.getKeywords(movieId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    add(movieId, it.keywords)
                    return@flatMap Observable.just(it)
                }
                .map { it.keywords }

    }

    fun add(movieId: Int, items: List<Keyword>) {
        val keywords = ArrayList<Keyword>()
        for (item in items) {
            keywords.add(item.copy(movieId = movieId))
        }

        /*dao.insert(keywords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()*/
    }
}