package org.michaelbel.moviemade.presentation.features.keywords

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface KeywordContract {

    interface View: BaseView<List<Movie>>

    interface Presenter: BasePresenter<View> {
        fun getMovies(keywordId: Int)
        fun getMoviesNext(keywordId: Int)
    }

    interface Repository {
        fun getMovies(keywordId: Int, page: Int): Observable<List<Movie>>
    }
}