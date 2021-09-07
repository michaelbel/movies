package org.michaelbel.domain

import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Result
import org.michaelbel.data.remote.model.Video
import retrofit2.Response
import javax.inject.Inject

class TrailersRepository @Inject constructor(
    private val api: Api
) {

    suspend fun trailers(movieId: Long, apiKey: String): Response<Result<Video>> {
        return api.trailers(movieId, apiKey)
    }
}