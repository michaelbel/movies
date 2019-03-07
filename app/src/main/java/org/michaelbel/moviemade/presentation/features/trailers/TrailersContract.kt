package org.michaelbel.moviemade.presentation.features.trailers

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Video
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface TrailersContract {

    interface View: BaseView<List<Video>>

    interface Presenter: BasePresenter<View> {
        fun getVideos(movieId: Int)
    }

    interface Repository {
        fun getVideos(movieId: Int): Observable<List<Video>>
    }
}