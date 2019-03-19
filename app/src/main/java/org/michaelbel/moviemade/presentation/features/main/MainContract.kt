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

        fun movies(keywordId: Int)
        fun moviesNext(keywordId: Int)

        fun moviesWatchlist(accountId: Int, sessionId: String)
        fun moviesWatchlistNext(accountId: Int, sessionId: String)

        fun moviesFavorite(accountId: Int, sessionId: String)
        fun moviesFavoriteNext(accountId: Int, sessionId: String)
    }

    interface Repository {
        fun movies(movieId: Int, list: String, page: Int): Observable<List<Movie>>
        fun movies(keywordId: Int, page: Int): Observable<List<Movie>>

        fun moviesWatchlist(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>>
        fun moviesFavorite(accountId: Int, sessionId: String, page: Int): Observable<List<Movie>>
    }
}