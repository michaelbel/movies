package org.michaelbel.moviemade.presentation.features.trailers

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.presentation.base.Presenter

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
                .subscribe({ (_, trailers) ->
                    val results = ArrayList(trailers)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_TRAILERS)
                        return@subscribe
                    }
                    view?.setTrailers(trailers)
                }, { view?.setError(EmptyViewMode.MODE_NO_TRAILERS) }))
    }

    override fun destroy() {
        disposable.dispose()
    }
}