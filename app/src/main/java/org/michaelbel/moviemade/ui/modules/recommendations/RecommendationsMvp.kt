package org.michaelbel.moviemade.ui.modules.recommendations

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface RecommendationsMvp : MvpView {

    fun setMovies(movies: List<Movie>)

    fun setError(@EmptyViewMode mode: Int)
}