package org.michaelbel.moviemade.presentation.features.favorites

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse.Companion.ASC
import org.michaelbel.moviemade.core.remote.AccountService
import java.util.*

class FavoriteRepository(private val service: AccountService): FavoritesContract.Repository {

    override fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>> =
        service.getFavoriteMovies(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, ASC, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.movies }
}