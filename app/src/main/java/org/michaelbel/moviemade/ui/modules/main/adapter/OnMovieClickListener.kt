package org.michaelbel.moviemade.ui.modules.main.adapter

import android.view.View

import org.michaelbel.moviemade.data.entity.Movie

interface OnMovieClickListener {

    fun onMovieClick(movie: Movie, view: View, position: Int)
}