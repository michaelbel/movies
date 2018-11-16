package org.michaelbel.moviemade.ui.modules.main

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface MainMvp : MvpView {

    fun setMovies(movies: List<Movie>, firstPage: Boolean)

    fun setError(@EmptyViewMode mode: Int)
}