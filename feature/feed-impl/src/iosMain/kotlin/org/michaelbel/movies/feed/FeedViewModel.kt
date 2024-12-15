@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
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
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentFeedView.first() }
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentMovieList.first() }
        )

    val pagingDataFlow: StateFlow<List<MoviePojo>> = currentMovieList.flatMapLatest { movieList ->
        flowOf(interactor.moviesResult(movieList.nameOrLocalList))
    }.catch {
        emptyList<List<MoviePojo>>()
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )
}