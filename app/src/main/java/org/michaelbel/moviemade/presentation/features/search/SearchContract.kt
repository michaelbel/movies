package org.michaelbel.moviemade.presentation.features.search

import io.reactivex.Observable
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.entity.MoviesResponse
import org.michaelbel.moviemade.core.utils.EmptyViewMode
import org.michaelbel.moviemade.presentation.base.BasePresenter

interface SearchContract {

    interface View {
        fun searchStart()
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter: BasePresenter<View> {
        fun search(query: String)
        fun loadNextResults()
    }

    interface Repository {
        fun search(query: String, page: Int) : Observable<MoviesResponse>
    }
}