package org.michaelbel.movies.feed

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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

    private val _pagingDataFlow = MutableStateFlow<List<MoviePojo>>(emptyList())
    val pagingDataFlow = _pagingDataFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val result = interactor.moviesResult(currentMovieList.value.nameOrLocalList)
            _pagingDataFlow.value = result
        }
    }
}