package org.michaelbel.moviemade.ui.modules.main

import com.arellomobile.mvp.MvpView

import org.michaelbel.moviemade.annotation.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Movie

interface ResultsMvp : MvpView {

    fun setResults(results: List<Movie>, firstPage: Boolean)

    fun setError(@EmptyViewMode mode: Int)
}