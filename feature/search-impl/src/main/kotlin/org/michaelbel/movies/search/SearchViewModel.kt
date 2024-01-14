package org.michaelbel.movies.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.search.remote.SearchMoviesRemoteMediator

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

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    val pagingItems: Flow<PagingData<MovieDb>> = query.flatMapLatest { query ->
        Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE
            ),
            remoteMediator = SearchMoviesRemoteMediator(
                interactor = interactor,
                query = query
            ),
            pagingSourceFactory = { interactor.moviesPagingSource(query) }
        ).flow
            .stateIn(
                scope = this,
                started = SharingStarted.Lazily,
                initialValue = PagingData.empty()
            )
            .cachedIn(this)
    }

    fun onChangeSearchQuery(query: String) {
        _query.value = query
    }
}