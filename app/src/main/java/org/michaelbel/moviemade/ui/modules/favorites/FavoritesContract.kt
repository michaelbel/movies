package org.michaelbel.moviemade.ui.modules.favorites

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface FavoritesContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun getFavoriteMovies(accountId: Int, sessionId: String)
        fun getFavoriteMoviesNext(accountId: Int, sessionId: String)
        fun onDestroy()
    }

    interface Repository {
        fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int) : Observable<MoviesResponse>
    }
}