package org.michaelbel.movies.feed

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor

class FeedViewModel(
    private val interactor: Interactor
): BaseViewModel() {

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentMovieList.first() }
        )
}