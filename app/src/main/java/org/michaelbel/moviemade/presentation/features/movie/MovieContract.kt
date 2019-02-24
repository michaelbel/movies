package org.michaelbel.moviemade.presentation.features.movie

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.AccountStates
import org.michaelbel.moviemade.core.entity.Mark
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface MovieContract {

    interface View {
        // fixme put movie object.
        fun setPoster(posterPath: String)
        fun setMovieTitle(title: String)
        fun setOverview(overview: String)
        fun setVoteAverage(voteAverage: Float)
        fun setVoteCount(voteCount: Int)
        fun setReleaseDate(releaseDate: String)
        fun setOriginalLanguage(originalLanguage: String)
        fun setRuntime(runtime: String)
        fun setTagline(tagline: String)
        fun setURLs(imdbId: String, homepage: String)
        fun setStates(fave: Boolean, watch : Boolean)
        fun onFavoriteChanged(mark: Mark)
        fun onWatchListChanged(mark: Mark)
        fun setCredits(casts: String, directors: String, writers: String, producers: String)
        fun setConnectionError()
        fun setGenres(genres : List<Int>)
        fun showComplete(movie: Movie)
    }

    interface Presenter: BasePresenter<View> {
        fun setDetailExtra(movie: Movie)
        fun getDetails(movieId: Int)
        fun markFavorite(accountId: Int, mediaId: Int, favorite: Boolean)
        fun addWatchlist(accountId: Int, mediaId: Int, watchlist: Boolean)
        fun getAccountStates(movieId: Int)
    }

    interface Repository {
        fun getDetails(movieId: Int) : Observable<Movie>
        fun markFavorite(accountId: Int, sessionId: String, mediaId: Int, favorite: Boolean) : Observable<Mark>
        fun addWatchlist(accountId: Int, sessionId: String, mediaId: Int, watchlist: Boolean) : Observable<Mark>
        fun getAccountStates(movieId: Int, sessionId: String) : Observable<AccountStates>
    }
}