package org.michaelbel.moviemade.presentation.features.search

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.SearchService
import org.michaelbel.moviemade.core.utils.AdultUtil
import org.michaelbel.moviemade.presentation.App
import java.util.*

class SearchRepository internal constructor(
        private val service: SearchService
): SearchContract.Repository {

    override fun search(query: String, page: Int): Observable<MoviesResponse> =
        service.searchMovies(TMDB_API_KEY, Locale.getDefault().language, query, page, AdultUtil.includeAdult(App.appContext), "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}