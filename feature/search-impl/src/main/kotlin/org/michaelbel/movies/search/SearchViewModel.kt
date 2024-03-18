@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
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
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val interactor: Interactor,
    networkManager: NetworkManager,
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

    val suggestionsFlow: StateFlow<List<SuggestionDb>> = interactor.suggestions()
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val searchHistoryMoviesFlow: StateFlow<List<MovieDb>> = interactor.moviesFlow(MovieDb.MOVIES_SEARCH_HISTORY, Int.MAX_VALUE)
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    val isSearchActive: StateFlow<Boolean> = interactor.isSearchActive

    val pagingDataFlow: Flow<PagingData<MovieDb>> = query
        .flatMapLatest { query -> interactor.moviesPagingData(query) }
        .cachedIn(this)

    init {
        loadSuggestions()
    }

    fun onChangeSearchQuery(query: String) {
        _query.value = query
    }

    fun onChangeActiveState(state: Boolean) {
        interactor.setSearchActive(state)
    }

    fun onSaveToHistory(movieId: Int) = launch {
        val movie: MovieDb = interactor.movie(query.value, movieId)
        interactor.insertMovie(MovieDb.MOVIES_SEARCH_HISTORY, movie)
    }

    fun onRemoveFromHistory(movieId: Int) = launch {
        interactor.removeMovie(MovieDb.MOVIES_SEARCH_HISTORY, movieId)
    }

    fun onClearSearchHistory() = launch {
        interactor.removeMovies(MovieDb.MOVIES_SEARCH_HISTORY)
    }

    private fun loadSuggestions() = launch {
        interactor.updateSuggestions()
    }
}