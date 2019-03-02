package org.michaelbel.moviemade.presentation.features.recommendations

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BaseContract

interface RcmdContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun getRcmdMovies(movieId: Int)
        fun getRcmdMoviesNext(movieId: Int)
    }

    interface Repository {
        fun getRcmdMovies(movieId: Int, page: Int): Observable<MoviesResponse>
    }
}