package org.michaelbel.moviemade.ui.modules.search

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface SearchContract {

    interface View {
        fun searchStart()
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun setView(view: View)
        fun search(query: String)
        fun loadNextResults()
        fun onDestroy()
    }

    interface Repository {
        fun search(query: String, page: Int) : Observable<MoviesResponse>
    }
}