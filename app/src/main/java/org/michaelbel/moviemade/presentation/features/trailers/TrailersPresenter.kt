package org.michaelbel.moviemade.presentation.features.trailers

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

class TrailersPresenter(repository: TrailersRepository): Presenter(), TrailersContract.Presenter {

    private var view: TrailersContract.View? = null
    private val repository: TrailersContract.Repository = repository

    override fun attach(view: TrailersContract.View) {
        this.view = view
    }

    override fun getVideos(movieId: Int) {
        if (NetworkUtil.isNetworkConnected().not()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        disposable.add(repository.getVideos(movieId)
                .doOnTerminate { view?.hideLoading() }
                .subscribe({
                    val results = ArrayList(it.trailers)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_TRAILERS)
                        return@subscribe
                    }
                    view?.setTrailers(it.trailers)
                }, { view?.setError(EmptyViewMode.MODE_NO_TRAILERS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}