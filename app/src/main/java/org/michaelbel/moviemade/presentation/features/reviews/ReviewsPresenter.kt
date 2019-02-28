package org.michaelbel.moviemade.presentation.features.reviews

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

class ReviewsPresenter(repository: ReviewsRepository): Presenter(), ReviewsContract.Presenter {

    private var view: ReviewsContract.View? = null
    private val repository: ReviewsContract.Repository = repository

    override fun attach(view: ReviewsContract.View) {
        this.view = view
    }

    override fun getReviews(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        disposable.add(repository.getReviews(movieId)
                .subscribe({
                    val results = ArrayList(it.reviews)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_REVIEWS)
                        return@subscribe
                    }
                    view?.setReviews(it.reviews)
                }, { view?.setError(EmptyViewMode.MODE_NO_REVIEWS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}