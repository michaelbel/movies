package org.michaelbel.moviemade.presentation.features.search

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.remote.Api
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.features.settings.AdultUtil
import java.util.*

class SearchRepository(private val service: Api): SearchContract.Repository {

    override fun search(query: String, page: Int): Observable<List<Movie>> =
        service.searchMovies(TMDB_API_KEY, Locale.getDefault().language, query, page, AdultUtil.includeAdult(App.appContext), "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.movies }
}