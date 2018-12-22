package org.michaelbel.moviemade.ui.modules.keywords

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.data.service.KeywordsService
import org.michaelbel.moviemade.utils.AdultUtil
import org.michaelbel.moviemade.utils.LangUtil

class KeywordRepository internal constructor(private val service: KeywordsService) : KeywordContract.Repository {

    override fun getMovies(keywordId: Int, page: Int): Observable<MoviesResponse> {
        return service.getMovies(keywordId, BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext), AdultUtil.includeAdult(Moviemade.appContext), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}