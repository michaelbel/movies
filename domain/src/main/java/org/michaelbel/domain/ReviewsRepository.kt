package org.michaelbel.domain

import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Result
import org.michaelbel.data.remote.model.Review
import retrofit2.Response
import javax.inject.Inject

class ReviewsRepository @Inject constructor(
    private val api: Api
) {

    suspend fun reviews(movieId: Long, apiKey: String, language: String, page: Int): Response<Result<Review>> {
        return api.reviews(movieId, apiKey, page)
    }
}