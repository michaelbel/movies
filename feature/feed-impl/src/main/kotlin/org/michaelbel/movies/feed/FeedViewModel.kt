package org.michaelbel.movies.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.inappupdate.di.InAppUpdate
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.mediator.MoviesRemoteMediator
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val interactor: Interactor,
    networkManager: NetworkManager,
    inAppUpdate: InAppUpdate
): BaseViewModel() {

    private val moviesList: String
        get() = if (isTmdbApiKeyEmpty) MovieDb.MOVIES_LOCAL_LIST else MovieResponse.NOW_PLAYING

    val pagingItems: Flow<PagingData<MovieDb>> = Pager(
        config = PagingConfig(
            pageSize = MovieResponse.DEFAULT_PAGE_SIZE
        ),
        remoteMediator = MoviesRemoteMediator(
            interactor = interactor,
            movieList = MovieResponse.NOW_PLAYING
        ),
        pagingSourceFactory = { interactor.moviesPagingSource(moviesList) }
    ).flow
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )
        .cachedIn(this)

    val account: StateFlow<AccountDb?> = interactor.account
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AccountDb.Empty
        )

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val isSettingsIconVisible: StateFlow<Boolean> = interactor.isSettingsIconVisible
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = true
        )

    var updateAvailableMessage: Boolean by mutableStateOf(false)

    init {
        inAppUpdate.onUpdateAvailableListener = { updateAvailable ->
            updateAvailableMessage = updateAvailable
        }
    }
}