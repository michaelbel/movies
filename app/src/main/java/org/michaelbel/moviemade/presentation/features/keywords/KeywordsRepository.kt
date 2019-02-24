package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.core.entity.KeywordsResponse
import org.michaelbel.moviemade.core.remote.MoviesService

class KeywordsRepository(private val service: MoviesService): KeywordsContract.Repository {

    override fun getKeywords(movieId: Int): Observable<KeywordsResponse> {
        return service.getKeywords(movieId, BuildConfig.TMDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}