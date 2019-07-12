package org.michaelbel.domain

import org.michaelbel.data.Review
import org.michaelbel.data.local.dao.ReviewsDao
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response
import java.util.*

class ReviewsRepository(private val api: Api, private val dao: ReviewsDao) {

    suspend fun reviews(movieId: Int, apiKey: String, language: String, page: Int): Response<Result<Review>> {
        return api.reviews(movieId, apiKey, page).await()
    }

    fun add(movieId: Int, items: List<Review>) {
        val reviews = ArrayList<Review>()
        for (item in items) {
            reviews.add(item.copy(movieId = movieId))
        }

        /*dao.insert(reviews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()*/
    }

    fun get(movieId: Int): List<Review> {
        return emptyList()
    }
}