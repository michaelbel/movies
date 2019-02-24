package org.michaelbel.moviemade.presentation.features.trailers

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.VideosResponse
import org.michaelbel.moviemade.core.remote.MoviesService
import org.michaelbel.moviemade.core.utils.LocalUtil
import org.michaelbel.moviemade.presentation.App

class TrailersRepository internal constructor(
        private val service: MoviesService
): TrailersContract.Repository {

    override fun getVideos(movieId: Int): Observable<VideosResponse> =
        service.getVideos(movieId, TMDB_API_KEY, LocalUtil.getLanguage(App.appContext))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}