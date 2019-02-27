package org.michaelbel.moviemade.presentation.features.reviews

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.presentation.base.Presenter

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
                .subscribe({ (_, _, reviews) ->
                    val results = ArrayList(reviews)
                    if (results.isEmpty()) {
                        view!!.setError(EmptyViewMode.MODE_NO_REVIEWS)
                        return@subscribe
                    }
                    view?.setReviews(reviews)
                }, { view?.setError(EmptyViewMode.MODE_NO_REVIEWS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}