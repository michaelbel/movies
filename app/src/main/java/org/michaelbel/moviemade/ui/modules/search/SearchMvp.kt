package org.michaelbel.moviemade.ui.modules.search

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface SearchMvp : MvpView {

    fun searchStart()

    fun setMovies(movies: List<Movie>)

    fun setSuggestions(movies: List<Movie>)

    fun setError(@EmptyViewMode mode: Int)
}