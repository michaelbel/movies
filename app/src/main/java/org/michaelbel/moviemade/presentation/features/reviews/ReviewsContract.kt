package org.michaelbel.moviemade.presentation.features.reviews

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Review
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface ReviewsContract {

    interface View: BaseView<List<Review>>

    interface Presenter: BasePresenter<View> {
        fun getReviews(movieId: Int)
    }

    interface Repository {
        fun getReviews(movieId: Int): Observable<List<Review>>
    }
}