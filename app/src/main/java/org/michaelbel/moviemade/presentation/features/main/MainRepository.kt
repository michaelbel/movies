package org.michaelbel.moviemade.presentation.features.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.Api
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.features.settings.AdultUtil
import java.util.*

class MainRepository(private val service: Api): MainContract.Repository {

    override fun movies(movieId: Int, list: String, page: Int): Observable<List<Movie>> {
        val response = if (movieId == 0)
            service.movies(list, TMDB_API_KEY, Locale.getDefault().language, page)
        else
            service.moviesById(movieId, list, TMDB_API_KEY, Locale.getDefault().language, page)

        return response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.movies }
    }

    override fun movies(keywordId: Int, page: Int): Observable<List<Movie>> =
            service.moviesByKeyword(keywordId, TMDB_API_KEY, Locale.getDefault().language, AdultUtil.includeAdult(App.appContext), page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.movies }

    override fun moviesWatchlist(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>> =
            service.moviesWatchlist(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, MoviesResponse.ASC, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.movies }

    override fun moviesFavorite(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>> =
            service.moviesFavorite(accountId, TMDB_API_KEY, sessionId, Locale.getDefault().language, MoviesResponse.ASC, page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.movies }
}