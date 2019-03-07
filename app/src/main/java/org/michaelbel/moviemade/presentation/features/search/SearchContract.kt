package org.michaelbel.moviemade.presentation.features.search

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.presentation.base.BasePresenter
import org.michaelbel.moviemade.presentation.base.BaseView

interface SearchContract {

    interface View: BaseView<List<Movie>> {
        fun searchStart()
    }

    interface Presenter: BasePresenter<View> {
        fun search(query: String)
        fun loadNextResults()
    }

    interface Repository {
        fun search(query: String, page: Int): Observable<List<Movie>>
    }
}