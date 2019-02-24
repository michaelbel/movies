package org.michaelbel.moviemade.presentation.features.main

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface MainContract {

    interface View {
        fun setLoading()
        fun setContent(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun getNowPlaying()
        fun getNowPlayingNext()
        fun getTopRated()
        fun getTopRatedNext()
        fun getUpcoming()
        fun getUpcomingNext()
    }

    interface Repository {
        fun getNowPlaying(page: Int): Observable<MoviesResponse>
        fun getTopRated(page: Int): Observable<MoviesResponse>
        fun getUpcoming(page: Int): Observable<MoviesResponse>
    }
}