package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.remote.Api

class KeywordsRepository(private val service: Api): KeywordsContract.Repository {

    override fun keywords(movieId: Int): Observable<List<Keyword>> =
        service.getKeywords(movieId, TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.keywords }
}