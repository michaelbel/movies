package org.michaelbel.moviemade.ui.modules.watchlist

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface WatchlistContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun getWatchlistMovies(accountId: Int, sessionId: String)
        fun getWatchlistMoviesNext(accountId: Int, sessionId: String)
        fun onDestroy()
    }

    interface Repository {
        fun getWatchlistMovies(accountId: Int, sessionId: String, page: Int) : Observable<MoviesResponse>
    }
}