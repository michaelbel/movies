package org.michaelbel.moviemade.ui.modules.recommendations

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface RcmdContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun getRcmdMovies(movieId: Int)
        fun getRcmdMoviesNext(movieId: Int)
        fun onDestroy()
    }

    interface Repository {
        fun getRcmdMovies(movieId: Int, page: Int) : Observable<MoviesResponse>
    }
}