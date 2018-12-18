package org.michaelbel.moviemade.ui.modules.main

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface MainContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun getNowPlaying()
        fun getNowPlayingNext()
        fun getTopRated()
        fun getTopRatedNext()
        fun getUpcoming()
        fun getUpcomingNext()
        fun onDestroy()
    }

    interface Repository {
        fun getNowPlaying(page: Int) : Observable<MoviesResponse>
        fun getTopRated(page: Int) : Observable<MoviesResponse>
        fun getUpcoming(page: Int) : Observable<MoviesResponse>
    }
}