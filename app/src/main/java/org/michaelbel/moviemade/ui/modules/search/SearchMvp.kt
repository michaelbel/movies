package org.michaelbel.moviemade.ui.modules.search

import com.arellomobile.mvp.MvpView

import org.michaelbel.moviemade.annotation.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Movie

interface SearchMvp : MvpView {

    fun searchStart()

    fun setResults(results: List<Movie>, firstPage: Boolean)

    fun setError(@EmptyViewMode mode: Int)
}