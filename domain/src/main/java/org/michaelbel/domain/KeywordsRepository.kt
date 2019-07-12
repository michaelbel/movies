package org.michaelbel.domain

import org.michaelbel.data.Keyword
import org.michaelbel.data.local.dao.KeywordsDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.KeywordsResponse
import retrofit2.Response
import java.util.*

class KeywordsRepository(private val api: Api, private val dao: KeywordsDao) {

    suspend fun keywords(movieId: Int, apiKey: String): Response<KeywordsResponse> {
        return api.keywords(movieId, apiKey).await()
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