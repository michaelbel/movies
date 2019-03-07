package org.michaelbel.moviemade.presentation.features.watchlist

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface WatchlistContract {

    interface View: BaseView<List<Movie>>

    interface Presenter: BasePresenter<View> {
        fun getWatchlistMovies(accountId: Int, sessionId: String)
        fun getWatchlistMoviesNext(accountId: Int, sessionId: String)
    }

    interface Repository {
        fun getWatchlistMovies(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>>
    }
}