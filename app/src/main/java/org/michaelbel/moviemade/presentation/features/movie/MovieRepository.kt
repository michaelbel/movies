package org.michaelbel.moviemade.presentation.features.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.*
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.utils.CONTENT_TYPE
import org.michaelbel.moviemade.core.utils.LocalUtil
import org.michaelbel.moviemade.presentation.App

class MovieRepository internal constructor(
        private val moviesService: MoviesService, private val accountService: AccountService
): MovieContract.Repository {

    override fun getDetails(movieId: Int): Observable<Movie> =
        moviesService.getDetails(movieId, TMDB_API_KEY, LocalUtil.getLanguage(App.appContext), Movie.MediaType.CREDITS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun markFavorite(accountId: Int, sessionId: String, mediaId: Int, favorite: Boolean): Observable<Mark> =
        accountService.markAsFavorite(CONTENT_TYPE, accountId, TMDB_API_KEY, sessionId, Fave(Movie.MediaType.MOVIE, mediaId, favorite))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addWatchlist(accountId: Int, sessionId: String, mediaId: Int, watchlist: Boolean): Observable<Mark> =
        accountService.addToWatchlist(CONTENT_TYPE, accountId, TMDB_API_KEY, sessionId, Watch(Movie.MediaType.MOVIE, mediaId, watchlist))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun getAccountStates(movieId: Int, sessionId: String): Observable<AccountStates> =
        moviesService.getAccountStates(movieId, TMDB_API_KEY, sessionId, "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}