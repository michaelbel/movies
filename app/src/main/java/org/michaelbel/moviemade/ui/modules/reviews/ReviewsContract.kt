package org.michaelbel.moviemade.ui.modules.reviews

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Review
import org.michaelbel.moviemade.data.entity.ReviewsResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface ReviewsContract {

    interface View {
        fun setReviews(reviews: List<Review>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun getReviews(movieId: Int)
        fun onDestroy()
    }

    interface Repository {
        fun getReviews(movieId: Int) : Observable<ReviewsResponse>
    }
}