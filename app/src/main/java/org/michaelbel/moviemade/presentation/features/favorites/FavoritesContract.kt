package org.michaelbel.moviemade.presentation.features.favorites

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BaseContract

interface FavoritesContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BaseContract.Presenter<View> {
        fun getFavoriteMovies(accountId: Int, sessionId: String)
        fun getFavoriteMoviesNext(accountId: Int, sessionId: String)
    }

    interface Repository {
        fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int): Observable<MoviesResponse>
    }
}