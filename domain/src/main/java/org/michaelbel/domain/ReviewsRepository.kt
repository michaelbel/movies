package org.michaelbel.domain

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.data.Review
import org.michaelbel.data.local.dao.ReviewsDao
import org.michaelbel.data.remote.Api
import java.util.*

class ReviewsRepository(private val api: Api, private val dao: ReviewsDao) {

    fun reviews(movieId: Int, apiKey: String, language: String, page: Int): Observable<List<Review>> {
        return api.getReviews(movieId, apiKey, language, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    add(movieId, it.reviews)
                    return@flatMap Observable.just(it)
                }
                //.map { dao.getAll(movieId) }
                .map { it.reviews }

    }

    fun add(movieId: Int, items: List<Review>) {
        val reviews = ArrayList<Review>()
        for (item in items) {
            reviews.add(item.copy(movieId = movieId))
        }

        dao.insert(reviews)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun get(movieId: Int): List<Review> {
        return emptyList()
    }
}