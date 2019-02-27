package org.michaelbel.moviemade.presentation.features.recommendations

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil

import java.util.ArrayList

import io.reactivex.disposables.CompositeDisposable
import org.michaelbel.moviemade.presentation.base.Presenter

class RcmdPresenter(repository: RcmdRepository): Presenter(), RcmdContract.Presenter {

    private var page: Int = 0
    private var view: RcmdContract.View? = null
    private val repository: RcmdContract.Repository = repository

    override fun attach(view: RcmdContract.View) {
        this.view = view
    }

    override fun getRcmdMovies(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getRcmdMovies(movieId, page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view?.setMovies(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getRcmdMoviesNext(movieId: Int) {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getRcmdMovies(movieId, page).subscribe { (_, movies) -> view!!.setMovies(movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}