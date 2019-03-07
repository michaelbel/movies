package org.michaelbel.moviemade.presentation.features.movie

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.AccountStates
import org.michaelbel.moviemade.core.entity.Mark
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface MovieContract {

    interface View {
        // fixme put movie object.
        fun movie(movie: Movie)
        fun movieExtra(movie: Movie)

        fun setURLs(imdbId: String, homepage: String)
        fun setStates(fave: Boolean, watch : Boolean)
        fun onFavoriteChanged(mark: Mark)
        fun onWatchListChanged(mark: Mark)
        fun setCredits(casts: String, directors: String, writers: String, producers: String)
        fun setConnectionError()
        fun showComplete(movie: Movie)
    }

    interface Presenter: BasePresenter<View> {
        fun setDetailExtra(movie: Movie)
        fun getDetails(sessionId: String, movieId: Int)
        fun markFavorite(sessionId: String, accountId: Int, mediaId: Int, favorite: Boolean)
        fun addWatchlist(sessionId: String, accountId: Int, mediaId: Int, watchlist: Boolean)
        fun getAccountStates(sessionId: String, movieId: Int)
    }

    interface Repository {
        fun getDetails(movieId: Int): Observable<Movie>
        fun markFavorite(accountId: Int, sessionId: String, mediaId: Int, favorite: Boolean): Observable<Mark>
        fun addWatchlist(accountId: Int, sessionId: String, mediaId: Int, watchlist: Boolean): Observable<Mark>
        fun getAccountStates(movieId: Int, sessionId: String): Observable<AccountStates>
    }
}