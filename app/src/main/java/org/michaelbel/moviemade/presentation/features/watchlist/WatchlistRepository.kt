package org.michaelbel.moviemade.presentation.features.watchlist

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.AccountService
import java.util.*

class WatchlistRepository internal constructor(
        private val service: AccountService
): WatchlistContract.Repository {

    override fun getWatchlistMovies(accountId: Int, sessionId: String, page: Int): Observable<MoviesResponse> =
        service.getWatchlistMovies(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, MoviesResponse.ASC, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}