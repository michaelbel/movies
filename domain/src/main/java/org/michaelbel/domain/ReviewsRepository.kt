package org.michaelbel.domain

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.data.local.dao.ReviewsDao
import org.michaelbel.data.local.model.ReviewLocal
import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.Review
import org.michaelbel.data.remote.model.base.Result
import retrofit2.Response
import java.util.*

class ReviewsRepository(private val api: Api, private val dao: ReviewsDao) {

    //region Remote

    suspend fun reviews(movieId: Long, apiKey: String, language: String, page: Int): Response<Result<Review>> {
        return api.reviews(movieId, apiKey, page).await()
    }

    //endregion

    // region Local

    suspend fun addAll(items: List<Review>) {
        val reviews = ArrayList<ReviewLocal>()
        items.forEach {
            val review = ReviewLocal(id = it.id, author = it.author)
            reviews.add(review)
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = dao.insert(reviews)
                withContext(Dispatchers.Main) {
                    Log.e("1488", "Вставка произошла успешно!")
                }
            } catch (e: Exception) {
                Log.e("1488", "Exception: $e")
            }
        }

        dao.insert(reviews)
    }

    //endregion
}