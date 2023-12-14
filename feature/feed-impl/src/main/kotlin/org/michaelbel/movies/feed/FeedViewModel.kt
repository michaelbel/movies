package org.michaelbel.movies.feed

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.feed.ktx.nameOrLocalList
import org.michaelbel.movies.feed.remote.MoviesRemoteMediator
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.platform.update.UpdateListener
import org.michaelbel.movies.platform.update.UpdateService

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val interactor: Interactor,
    private val notificationClient: NotificationClient,
    private val updateService: UpdateService,
    networkManager: NetworkManager
): BaseViewModel() {

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

    val currentFeedView: StateFlow<FeedView> = interactor.currentFeedView
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentFeedView.first() }
        )

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentMovieList.first() }
        )

    val pagingItems: Flow<PagingData<MovieDb>> = currentMovieList.flatMapLatest { movieList ->
        Pager(
            config = PagingConfig(
                pageSize = MovieResponse.DEFAULT_PAGE_SIZE
            ),
            remoteMediator = MoviesRemoteMediator(
                interactor = interactor,
                movieList = movieList.name
            ),
            pagingSourceFactory = { interactor.moviesPagingSource(movieList.nameOrLocalList) }
        ).flow
            .stateIn(
                scope = this,
                started = SharingStarted.Lazily,
                initialValue = PagingData.empty()
            )
            .cachedIn(this)
    }

    private var _notificationsPermissionRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val notificationsPermissionRequired: StateFlow<Boolean> = _notificationsPermissionRequired.asStateFlow()

    var updateAvailableMessage: Boolean by mutableStateOf(false)

    init {
        updateService.setUpdateAvailableListener(object: UpdateListener {
            override fun onAvailable(result: Boolean) {
                updateAvailableMessage = result
            }
        })
        subscribeNotificationsPermissionRequired()
    }

    fun startUpdate(activity: Activity) {
        updateService.startUpdate(activity)
    }

    fun onNotificationBottomSheetHide() = launch {
        _notificationsPermissionRequired.tryEmit(false)
        notificationClient.updateNotificationExpireTime()
    }

    private fun subscribeNotificationsPermissionRequired() = launch {
        _notificationsPermissionRequired.tryEmit(
            notificationClient.notificationsPermissionRequired(NOTIFICATIONS_PERMISSION_DELAY)
        )
    }

    private companion object {
        private const val NOTIFICATIONS_PERMISSION_DELAY = 2_000L
    }
}