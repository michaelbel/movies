package org.michaelbel.moviemade.ui.modules.keywords

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface KeywordMvp : MvpView {

    fun setError(@EmptyViewMode mode: Int)

    fun setMovies(movies: List<Movie>)
}