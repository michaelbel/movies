package org.michaelbel.moviemade.ui.modules.search

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.data.service.SearchService
import org.michaelbel.moviemade.utils.AdultUtil
import org.michaelbel.moviemade.utils.LangUtil

class SearchRepository internal constructor(private val service: SearchService) : SearchContract.Repository {

    override fun search(query: String, page: Int): Observable<MoviesResponse> {
        return service.searchMovies(BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), query, page, AdultUtil.includeAdult(Moviemade.appContext), "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}