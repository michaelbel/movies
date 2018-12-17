package org.michaelbel.moviemade.ui.modules.favorites

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.utils.EmptyViewMode

interface FavoritesMvp : MvpView {

    fun showLoading()

    fun hideLoading()

    fun setMovies(movies: List<Movie>)

    fun setError(@EmptyViewMode mode: Int)
}