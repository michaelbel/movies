package org.michaelbel.moviemade.presentation.features.watchlist

import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import java.util.*

class WatchlistPresenter(repository: WatchlistRepository): Presenter(), WatchlistContract.Presenter {

    private var page: Int = 0
    private var view: WatchlistContract.View? = null
    private val repository: WatchlistContract.Repository = repository

    override fun attach(view: WatchlistContract.View) {
        this.view = view
    }

    override fun getWatchlistMovies(accountId: Int, sessionId: String) {
        if (!NetworkUtil.isNetworkConnected()) {
            view!!.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getWatchlistMovies(accountId, sessionId, page)
                .subscribe({ (_, movies) ->
                    val results = ArrayList(movies)
                    if (results.isEmpty()) {
                        view?.setError(EmptyViewMode.MODE_NO_MOVIES)
                        return@subscribe
                    }
                    view?.setMovies(results)
                }, { view?.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getWatchlistMoviesNext(accountId: Int, sessionId: String) {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getWatchlistMovies(accountId, sessionId, page).subscribe { (_, movies) -> view?.setMovies(movies) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}