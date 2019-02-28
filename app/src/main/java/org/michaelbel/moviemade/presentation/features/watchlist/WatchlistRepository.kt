package org.michaelbel.moviemade.presentation.features.watchlist

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.consts.Order
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.utils.LocalUtil
import org.michaelbel.moviemade.presentation.App

class WatchlistRepository internal constructor(
        private val service: AccountService
): WatchlistContract.Repository {

    override fun getWatchlistMovies(accountId: Int, sessionId: String, page: Int): Observable<MoviesResponse> =
        service.getWatchlistMovies(accountId, TMDB_API_KEY, sessionId, LocalUtil.getLanguage(App.appContext), Order.ASC, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}