package org.michaelbel.moviemade.ui.modules.movie

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.constants.CREDITS
import org.michaelbel.moviemade.data.constants.MOVIE
import org.michaelbel.moviemade.data.entity.*
import org.michaelbel.moviemade.data.service.AccountService
import org.michaelbel.moviemade.data.service.MoviesService
import org.michaelbel.moviemade.utils.CONTENT_TYPE
import org.michaelbel.moviemade.utils.LangUtil

class MovieRepository internal constructor(private val moviesService: MoviesService, private val accountService: AccountService) : MovieContract.Repository {

    override fun getDetails(movieId: Int): Observable<Movie> {
        return moviesService.getDetails(movieId, BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), CREDITS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun markFavorite(accountId: Int, sessionId: String, mediaId: Int, favorite: Boolean): Observable<Mark> {
        return accountService.markAsFavorite(CONTENT_TYPE, accountId, BuildConfig.TMDB_API_KEY, sessionId, Fave(MOVIE, mediaId, favorite))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun addWatchlist(accountId: Int, sessionId: String, mediaId: Int, watchlist: Boolean): Observable<Mark> {
        return accountService.addToWatchlist(CONTENT_TYPE, accountId, BuildConfig.TMDB_API_KEY, sessionId, Watch(MOVIE, mediaId, watchlist))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getAccountStates(movieId: Int, sessionId: String): Observable<AccountStates> {
        return moviesService.getAccountStates(movieId, BuildConfig.TMDB_API_KEY, sessionId, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}