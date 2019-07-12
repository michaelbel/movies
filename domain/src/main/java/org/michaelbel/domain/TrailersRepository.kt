package org.michaelbel.domain

import org.michaelbel.data.Video
import org.michaelbel.data.local.dao.TrailersDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response

class TrailersRepository(private val api: Api, private val dao: TrailersDao): Repository() {

    suspend fun trailers(movieId: Int, apiKey: String): Response<Result<Video>> {
        return api.trailers(movieId, apiKey).await()
    }

    fun add(movieId: Int, items: List<Video>) {
        val trailers = ArrayList<Video>()
        for (item in items) {
            trailers.add(item.copy(movieId = movieId))
        }

        /*dao.insert(trailers)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()*/
    }
}