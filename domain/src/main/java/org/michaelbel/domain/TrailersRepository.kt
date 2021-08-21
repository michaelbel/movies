package org.michaelbel.domain

import org.michaelbel.data.local.dao.TrailersDao
import org.michaelbel.data.local.model.TrailerLocal
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Video
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response

class TrailersRepository(private val api: Api, private val dao: TrailersDao): Repository() {

    //region Remote

    suspend fun trailers(movieId: Long, apiKey: String): Response<Result<Video>> {
        return api.trailers(movieId, apiKey)
    }

    //endregion

    // region Local

    suspend fun addAll(items: List<Video>) {
        val trailers = java.util.ArrayList<TrailerLocal>()
        items.forEach {
            val trailer = TrailerLocal(id = it.id, title = it.name)
            trailers.add(trailer)
        }
        dao.insert(trailers)
    }

    //endregion
}