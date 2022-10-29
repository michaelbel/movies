package org.michaelbel.movies.feed

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus

@HiltViewModel
internal class FeedViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor,
    settingsInteractor: SettingsInteractor,
    networkManager: NetworkManager
): BaseViewModel() {

    val pagingItems: Flow<PagingData<MovieData>> = Pager(
        PagingConfig(
            pageSize = MovieData.DEFAULT_PAGE_SIZE
        )
    ) {
        MoviesPagingSource(movieInteractor)
    }.flow
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )
        .cachedIn(this)

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val isSettingsIconVisible: StateFlow<Boolean> = settingsInteractor.isSettingsIconVisible
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = true
        )
}