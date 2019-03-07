package org.michaelbel.moviemade.presentation.features.watchlist

import org.michaelbel.moviemade.core.net.NetworkUtil
import org.michaelbel.moviemade.presentation.base.Presenter
import retrofit2.HttpException

class WatchlistPresenter(
        private val repository: WatchlistContract.Repository
): Presenter(), WatchlistContract.Presenter {

    private var page: Int = 0
    private lateinit var view: WatchlistContract.View

    override fun attach(view: WatchlistContract.View) {
        this.view = view
    }

    override fun getWatchlistMovies(accountId: Int, sessionId: String) {
        /*if (!NetworkUtil.isNetworkConnected()) {
            view?.error(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }*/

        page = 1
        disposable.add(repository.getWatchlistMovies(accountId, sessionId, page)
                .subscribe({
                    if (it.isEmpty()) {
                        view.error(1)
                        return@subscribe
                    }
                    view.content(it) }, { view.error((it as HttpException).code()) }
                )
        )
    }

    override fun getWatchlistMoviesNext(accountId: Int, sessionId: String) {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getWatchlistMovies(accountId, sessionId, page)
                .subscribe { view.content(it) })
    }

    override fun destroy() {
        disposable.dispose()
    }
}