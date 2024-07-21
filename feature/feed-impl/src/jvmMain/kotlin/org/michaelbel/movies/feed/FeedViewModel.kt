@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.ktx.nameOrLocalList
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo

class FeedViewModel(
    private val interactor: Interactor
): BaseViewModel() {

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentFeedView.first() }
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentMovieList.first() }
        )

    val pagingDataFlow: StateFlow<List<MoviePojo>> = currentMovieList.flatMapLatest { movieList ->
        flowOf(interactor.moviesResult(movieList.nameOrLocalList))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
}