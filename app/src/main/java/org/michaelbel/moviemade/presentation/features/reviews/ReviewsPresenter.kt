package org.michaelbel.moviemade.presentation.features.reviews

import org.michaelbel.moviemade.core.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter

class ReviewsPresenter(
        private val repository: ReviewsContract.Repository
): Presenter(), ReviewsContract.Presenter {

    private lateinit var view: ReviewsContract.View

    override fun attach(view: ReviewsContract.View) {
        this.view = view
    }

    override fun getReviews(movieId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
        }

        disposable.add(repository.getReviews(movieId)
                .doOnSubscribe { view.loading(true) }
                .doAfterTerminate { view.loading(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(EmptyViewMode.MODE_NO_REVIEWS)
                        return@subscribe
                    }
                    view.content(it)
                }, { view.error(EmptyViewMode.MODE_NO_REVIEWS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}