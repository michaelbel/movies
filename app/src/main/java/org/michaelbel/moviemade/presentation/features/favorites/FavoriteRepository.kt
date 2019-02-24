package org.michaelbel.moviemade.presentation.features.favorites

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.core.consts.Order
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.AccountService
import org.michaelbel.moviemade.core.utils.LocalUtil

class FavoriteRepository internal constructor(
        private val service: AccountService
): FavoritesContract.Repository {

    override fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int): Observable<MoviesResponse> {
        return service.getFavoriteMovies(accountId, BuildConfig.TMDB_API_KEY, sessionId, LocalUtil.getLanguage(App.appContext), Order.ASC, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}