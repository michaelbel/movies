package org.michaelbel.moviemade.presentation.features.watchlist

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BaseContract

interface WatchlistContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun getWatchlistMovies(accountId: Int, sessionId: String)
        fun getWatchlistMoviesNext(accountId: Int, sessionId: String)
    }

    interface Repository {
        fun getWatchlistMovies(accountId: Int, sessionId: String, page: Int): Observable<MoviesResponse>
    }
}