@file:OptIn(ExperimentalCoroutinesApi::class)

package org.michaelbel.movies.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
import org.michaelbel.movies.common.exceptions.AccountDetailsException
import org.michaelbel.movies.common.exceptions.CreateSessionException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.persistence.database.entity.AccountPojo
import org.michaelbel.movies.persistence.database.entity.MoviePojo

class FeedViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor,
    private val notificationClient: NotificationClient,
    networkManager: NetworkManager
): BaseViewModel() {

    private val requestToken: String? = savedStateHandle["request_token"]
    private val approved: Boolean? = savedStateHandle["approved"]

    val account: StateFlow<AccountPojo?> = interactor.account
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AccountPojo.Empty
        )

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

    val currentMovieList: StateFlow<MovieList> = interactor.currentMovieList
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = runBlocking { interactor.currentMovieList.first() }
        )

    val pagingDataFlow: Flow<PagingData<MoviePojo>> = currentMovieList
        .flatMapLatest { movieList -> interactor.moviesPagingData(movieList) }
        .cachedIn(this)

    private var _notificationsPermissionRequired: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val notificationsPermissionRequired: StateFlow<Boolean> = _notificationsPermissionRequired.asStateFlow()

    var isAuthFailureSnackbarShowed: Boolean by mutableStateOf(false)

    init {
        authorizeAccount(requestToken, approved)
        subscribeNotificationsPermissionRequired()
    }

    override fun handleError(throwable: Throwable) {
        when (throwable) {
            is CreateSessionException -> isAuthFailureSnackbarShowed = true
            is AccountDetailsException -> isAuthFailureSnackbarShowed = true
            else -> super.handleError(throwable)
        }
    }

    fun onNotificationBottomSheetHide() = launch {
        _notificationsPermissionRequired.tryEmit(false)
        notificationClient.updateNotificationExpireTime()
    }

    fun onSnackbarDismissed() {
        isAuthFailureSnackbarShowed = false
    }

    private fun authorizeAccount(requestToken: String?, approved: Boolean?) {
        if (requestToken == null || approved == null) return
        launch {
            interactor.run {
                createSession(requestToken)
                accountDetails()
            }
        }
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