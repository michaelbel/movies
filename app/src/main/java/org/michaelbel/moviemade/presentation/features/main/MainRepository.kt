package org.michaelbel.moviemade.presentation.features.main

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.remote.MoviesService
import java.util.*

class MainRepository(private val service: MoviesService): MainContract.Repository {

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
}