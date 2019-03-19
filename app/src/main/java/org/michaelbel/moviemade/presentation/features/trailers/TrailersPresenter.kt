package org.michaelbel.moviemade.presentation.features.trailers

import org.michaelbel.moviemade.core.errors.EmptyViewMode
import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter

class TrailersPresenter(
        private val repository: TrailersContract.Repository
): Presenter(), TrailersContract.Presenter {

    private lateinit var view: TrailersContract.View

    override fun attach(view: TrailersContract.View) {
        this.view = view
    }

    override fun trailers(movieId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        disposable.add(repository.trailers(movieId)
                .doOnSubscribe { view.loading(true) }
                .doOnTerminate { view.loading(false) }
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(EmptyViewMode.MODE_NO_TRAILERS)
                        return@subscribe
                    }
                    view.content(it)
                }, { view.error(EmptyViewMode.MODE_NO_TRAILERS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}