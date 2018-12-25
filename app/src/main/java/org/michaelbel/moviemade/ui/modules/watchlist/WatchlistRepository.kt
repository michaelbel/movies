package org.michaelbel.moviemade.ui.modules.watchlist

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.constants.ASC
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.data.service.AccountService
import org.michaelbel.moviemade.utils.LangUtil

class WatchlistRepository internal constructor(private val service: AccountService) : WatchlistContract.Repository {

    override fun getWatchlistMovies(accountId: Int, sessionId: String, page: Int): Observable<MoviesResponse> {
        return service.getWatchlistMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, LangUtil.getLanguage(Moviemade.appContext), ASC, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}