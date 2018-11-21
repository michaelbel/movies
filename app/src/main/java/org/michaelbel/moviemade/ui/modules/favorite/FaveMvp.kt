package org.michaelbel.moviemade.ui.modules.favorite

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface FaveMvp : MvpView {

    fun setMovies(movies: List<Movie>)

    fun setError(@EmptyViewMode mode: Int)
}