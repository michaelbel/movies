@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.interactor.ktx.nameOrLocalList
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo

class FeedViewModel(
    private val interactor: Interactor,
    private val notificationClient: NotificationClient,
    networkManager: NetworkManager
): BaseViewModel() {

    val account: StateFlow<AccountPojo?> = interactor.account
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = AccountPojo.Empty
        )

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

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

    val pagingDataFlow: Flow<PagingData<MoviePojo>> = currentMovieList
        .flatMapLatest { movieList -> interactor.moviesPagingData(movieList) }
        .cachedIn(scope)

    val pagingDataFlow2: StateFlow<List<MoviePojo>> = currentMovieList.flatMapLatest { movieList ->
        flowOf(interactor.moviesResult(movieList.nameOrLocalList))
    }.catch {
        emptyList<List<MoviePojo>>()
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private var _notificationsPermissionRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val notificationsPermissionRequired: StateFlow<Boolean> get() = _notificationsPermissionRequired.asStateFlow()

    init {
        subscribeNotificationsPermissionRequired()
    }

    fun onNotificationBottomSheetHide() = scope.launch {
        _notificationsPermissionRequired.tryEmit(false)
        notificationClient.updateNotificationExpireTime()
    }

    private fun subscribeNotificationsPermissionRequired() = scope.launch {
        _notificationsPermissionRequired.tryEmit(
            notificationClient.notificationsPermissionRequired(NOTIFICATIONS_PERMISSION_DELAY)
        )
    }

    private companion object {
        private const val NOTIFICATIONS_PERMISSION_DELAY = 2_000L
    }
}