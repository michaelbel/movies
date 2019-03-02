package org.michaelbel.moviemade.presentation.features.trailers

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.core.entity.VideosResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BaseContract

interface TrailersContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun setTrailers(trailers: List<Video>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun getVideos(movieId: Int)
    }

    interface Repository {
        fun getVideos(movieId: Int): Observable<VideosResponse>
    }
}