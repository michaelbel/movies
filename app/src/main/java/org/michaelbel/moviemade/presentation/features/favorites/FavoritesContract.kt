package org.michaelbel.moviemade.presentation.features.favorites

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface FavoritesContract {

    interface View: BaseView<List<Movie>>

    interface Presenter: BasePresenter<View> {
        fun getFavoriteMovies(accountId: Int, sessionId: String)
        fun getFavoriteMoviesNext(accountId: Int, sessionId: String)
    }

    interface Repository {
        fun getFavoriteMovies(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>>
    }
}