@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Query

class SearchViewModel(
    private val interactor: Interactor,
    networkManager: NetworkManager
): BaseViewModel() {

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentFeedView.first() }
        )

    val suggestionsFlow: StateFlow<List<SuggestionPojo>> = interactor.suggestions()
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val searchHistoryMoviesFlow: StateFlow<List<MoviePojo>> = interactor.moviesFlow(MoviePojo.MOVIES_SEARCH_HISTORY, Int.MAX_VALUE)
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    val isSearchActive: StateFlow<Boolean> = interactor.isSearchActive

    val pagingDataFlow: Flow<PagingData<MoviePojo>> = query
        .flatMapLatest { query -> interactor.moviesPagingData(query) }
        .cachedIn(this)

    init {
        loadSuggestions()
    }

    fun onChangeSearchQuery(query: Query) {
        _query.value = query
    }

    fun onChangeActiveState(state: Boolean) {
        interactor.setSearchActive(state)
    }

    fun onSaveToHistory(movieId: MovieId) = launch {
        val movie = interactor.movie(query.value, movieId)
        interactor.insertMovie(MoviePojo.MOVIES_SEARCH_HISTORY, movie)
    }

    fun onRemoveFromHistory(movieId: MovieId) = launch {
        interactor.removeMovie(MoviePojo.MOVIES_SEARCH_HISTORY, movieId)
    }

    fun onClearSearchHistory() = launch {
        interactor.removeMovies(MoviePojo.MOVIES_SEARCH_HISTORY)
    }

    private fun loadSuggestions() = launch {
        interactor.updateSuggestions()
    }
}