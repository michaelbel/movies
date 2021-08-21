package org.michaelbel.domain

import org.michaelbel.data.local.dao.KeywordsDao
import org.michaelbel.data.local.model.KeywordLocal
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.data.remote.model.KeywordsResponse
import retrofit2.Response

class KeywordsRepository(private val api: Api, private val dao: KeywordsDao) {

    //region Remote

    suspend fun keywords(movieId: Long, apiKey: String): Response<KeywordsResponse> {
        return api.keywords(movieId, apiKey)
    }

    //endregion

    //region Local

    suspend fun addAll(id: Long, items: List<Keyword>) {
        val keywords = ArrayList<KeywordLocal>()
        items.forEach {
            val keyword = KeywordLocal(id = it.id, name = it.name, movieId = id)
            keywords.add(keyword)
        }
        dao.insert(keywords)
    }

    //endregion
}