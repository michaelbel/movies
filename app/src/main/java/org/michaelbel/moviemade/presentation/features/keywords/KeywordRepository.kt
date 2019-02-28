package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.remote.KeywordsService
import org.michaelbel.moviemade.core.utils.AdultUtil
import org.michaelbel.moviemade.core.utils.LocalUtil
import org.michaelbel.moviemade.presentation.App

class KeywordRepository internal constructor(
        private val service: KeywordsService
): KeywordContract.Repository {

    override fun getMovies(keywordId: Int, page: Int): Observable<MoviesResponse> {
        return service.getMovies(keywordId, TMDB_API_KEY, LocalUtil.getLanguage(App.appContext), AdultUtil.includeAdult(App.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}