package org.michaelbel.moviemade.ui.modules.trailers

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Video
import org.michaelbel.moviemade.data.entity.VideosResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface TrailersContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun setTrailers(trailers: List<Video>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun getVideos(movieId: Int)
        fun onDestroy()
    }

    interface Repository {
        fun getVideos(movieId: Int) : Observable<VideosResponse>
    }
}