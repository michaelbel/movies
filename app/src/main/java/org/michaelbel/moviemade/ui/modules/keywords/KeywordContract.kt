package org.michaelbel.moviemade.ui.modules.keywords

import io.reactivex.Observable
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.MoviesResponse
import org.michaelbel.moviemade.utils.EmptyViewMode

interface KeywordContract {

    interface View {
        fun setMovies(movies: List<Movie>)
        fun setError(@EmptyViewMode mode: Int)
    }

    interface Presenter {
        fun getMovies(keywordId: Int)
        fun getMoviesNext(keywordId: Int)
        fun onDestroy()
    }

    interface Repository {
        fun getMovies(keywordId: Int, page: Int) : Observable<MoviesResponse>
    }
}