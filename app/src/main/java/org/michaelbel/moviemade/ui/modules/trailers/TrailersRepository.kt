package org.michaelbel.moviemade.ui.modules.trailers

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.michaelbel.moviemade.BuildConfig
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.data.entity.VideosResponse
import org.michaelbel.moviemade.data.service.MoviesService
import org.michaelbel.moviemade.utils.LangUtil

class TrailersRepository internal constructor(private val service: MoviesService) : TrailersContract.Repository {

    override fun getVideos(movieId: Int): Observable<VideosResponse> {
        return service.getVideos(movieId, BuildConfig.TMDB_API_KEY, LangUtil.getLanguage(Moviemade.appContext))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}