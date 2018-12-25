package org.michaelbel.moviemade.ui.modules.similar

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface SimilarContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun getSimilarMovies(movieId: Int)
        fun getSimilarMoviesNext(movieId: Int)
        fun onDestroy()
    }

    interface Repository {
        fun getSimilarMovies(movieId: Int, page: Int) : Observable<MoviesResponse>
    }
}