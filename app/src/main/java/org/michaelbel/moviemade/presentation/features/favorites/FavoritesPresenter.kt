package org.michaelbel.moviemade.presentation.features.favorites

import org.michaelbel.moviemade.presentation.base.Presenter
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.core.utils.NetworkUtil
import java.util.*

class FavoritesPresenter(repository: FavoriteRepository): Presenter(), FavoritesContract.Presenter {

    private var page: Int = 0
    private var view: FavoritesContract.View? = null
    private val repository: FavoritesContract.Repository = repository

    override fun attach(view: FavoritesContract.View) {
        this.view = view
    }

    override fun getFavoriteMovies(accountId: Int, sessionId: String) {
        if (!NetworkUtil.isNetworkConnected()) {
            view?.setError(EmptyViewMode.MODE_NO_CONNECTION)
            return
        }

        page = 1
        disposable.add(repository.getFavoriteMovies(accountId, sessionId, page)
            .subscribe({ (_, movies) ->
                val results = ArrayList(movies)
                if (results.isEmpty()) {
                    view!!.setError(EmptyViewMode.MODE_NO_MOVIES)
                    return@subscribe
                }
                view?.setMovies(results)
            }, { view?.setError(EmptyViewMode.MODE_NO_MOVIES) }))
    }

    override fun getFavoriteMoviesNext(accountId: Int, sessionId: String) {
        if (!NetworkUtil.isNetworkConnected()) return

        page++
        disposable.add(repository.getFavoriteMovies(accountId, sessionId, page).subscribe { (_, movies) -> view!!.setMovies(movies)})
    }

    override fun destroy() {
        disposable.dispose()
    }
}