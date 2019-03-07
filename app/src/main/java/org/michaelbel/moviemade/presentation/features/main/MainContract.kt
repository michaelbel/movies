package org.michaelbel.moviemade.presentation.features.main

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface MainContract {

    interface View: BaseView<List<Movie>>

    interface Presenter: BasePresenter<View> {
        fun movies(movieId: Int = 0, list: String)
        fun moviesNext(movieId: Int = 0, list: String)
    }

    interface Repository {
        fun movies(movieId: Int, list: String, page: Int): Observable<List<Movie>>
    }
}