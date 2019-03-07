package org.michaelbel.moviemade.presentation.features.trailers

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.core.remote.MoviesService
import java.util.*

class TrailersRepository(private val service: MoviesService): TrailersContract.Repository {

    override fun getVideos(movieId: Int): Observable<List<Video>> =
        service.getVideos(movieId, TMDB_API_KEY, Locale.getDefault().language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.trailers }
}