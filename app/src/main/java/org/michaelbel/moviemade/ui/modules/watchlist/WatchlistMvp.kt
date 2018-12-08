package org.michaelbel.moviemade.ui.modules.watchlist

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface WatchlistMvp : MvpView {

    fun setMovies(movies: List<Movie>)

    fun setError(@EmptyViewMode mode: Int)
}