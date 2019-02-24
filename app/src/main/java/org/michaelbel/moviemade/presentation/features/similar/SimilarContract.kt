package org.michaelbel.moviemade.presentation.features.similar

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface SimilarContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(msg: Throwable, code: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun getSimilarMovies(movieId: Int)
        fun getSimilarMoviesNext(movieId: Int)
    }

    interface Repository {
        fun getSimilarMovies(movieId: Int, page: Int) : Observable<MoviesResponse>
    }
}