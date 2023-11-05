package org.michaelbel.movies.feed.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.feed.ktx.titleText
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.ui.compose.NotificationBottomSheet
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.isFailure
import org.michaelbel.movies.ui.ktx.isLoading
import org.michaelbel.movies.ui.ktx.throwable
import java.net.UnknownHostException

@Composable
fun FeedRoute(
    onNavigateToAccount: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    onStartUpdateFlow: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val pagingItems: LazyPagingItems<MovieDb> = viewModel.pagingItems.collectAsLazyPagingItems()
    val account: AccountDb? by viewModel.account.collectAsStateWithLifecycle()
    val currentFeedView: FeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList: MovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val notificationsPermissionRequired: Boolean by viewModel.notificationsPermissionRequired.collectAsStateWithLifecycle()
    val networkStatus: NetworkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val isUpdateAvailable: Boolean by rememberUpdatedState(viewModel.updateAvailableMessage)

    FeedScreenContent(
        pagingItems = pagingItems,
        account = account.orEmpty,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        currentMovieList = currentMovieList,
        notificationsPermissionRequired = notificationsPermissionRequired,
        isUpdateIconVisible = isUpdateAvailable,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails,
        onUpdateIconClick = onStartUpdateFlow,
        onNotificationBottomSheetHideClick = viewModel::onNotificationBottomSheetHide,
        modifier = modifier
    )
}

@Composable
private fun FeedScreenContent(
    pagingItems: LazyPagingItems<MovieDb>,
    account: AccountDb,
    networkStatus: NetworkStatus,
    currentFeedView: FeedView,
    currentMovieList: MovieList,
    notificationsPermissionRequired: Boolean,
    isUpdateIconVisible: Boolean,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    onUpdateIconClick: () -> Unit,
    onNotificationBottomSheetHideClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context: Context = LocalContext.current
    val scope: CoroutineScope = rememberCoroutineScope()
    val lazyListState: LazyListState = rememberLazyListState()
    val lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val notificationBottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            confirmValueChange = { sheetValue ->
                if (sheetValue == SheetValue.Hidden) {
                    onNotificationBottomSheetHideClick()
                }
                true
            },
            skipHiddenState = false
        )
    )
    val onNotificationBottomSheetShow: () -> Unit = {
        scope.launch {
            notificationBottomSheetScaffoldState.bottomSheetState.expand()
        }
    }
    val onNotificationBottomSheetHide: () -> Unit = {
        scope.launch {
            notificationBottomSheetScaffoldState.bottomSheetState.hide()
            onNotificationBottomSheetHideClick()
        }
    }

    val onScrollToTop: () -> Unit = {
        scope.launch {
            lazyListState.animateScrollToItem(0)
        }
        scope.launch {
            lazyStaggeredGridState.animateScrollToItem(0)
        }
    }

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    if (pagingItems.isFailure && pagingItems.throwable is ApiKeyNotNullException) {
        onShowSnackbar(stringResource(R.string.feed_error_api_key_null))
    }

    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.throwable is UnknownHostException) {
        pagingItems.retry()
    }

    val settingsPanelContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    if (notificationsPermissionRequired) {
        onNotificationBottomSheetShow()
    }

    BackHandler(enabled = notificationBottomSheetScaffoldState.bottomSheetState.isVisible) {
        onNotificationBottomSheetHide()
    }

    BottomSheetScaffold(
        sheetContent = {
            NotificationBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                onBottomSheetHide = onNotificationBottomSheetHide
            )
        },
        scaffoldState = notificationBottomSheetScaffoldState,
        sheetPeekHeight = 0.dp
    ) { bottomSheetPaddingValues ->
        val topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = modifier
                .padding(bottomSheetPaddingValues)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                FeedToolbar(
                    title = currentMovieList.titleText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableWithoutRipple { onScrollToTop() },
                    account = account,
                    isUpdateIconVisible = isUpdateIconVisible,
                    onAuthIconClick = {
                        when {
                            isTmdbApiKeyEmpty -> onShowSnackbar(context.getString(R.string.feed_error_api_key_null))
                            else -> onNavigateToAuth()
                        }
                    },
                    onAccountIconClick = onNavigateToAccount,
                    onUpdateIconClick = onUpdateIconClick,
                    topAppBarScrollBehavior = topAppBarScrollBehavior,
                    onSettingsIconClick = onNavigateToSettings
                )
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState
                )
            },
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) { paddingValues ->
            when {
                pagingItems.isLoading -> {
                    when (currentFeedView) {
                        is FeedView.FeedList -> {
                            FeedCellLoading(
                                modifier = Modifier.fillMaxSize().padding(top = 4.dp),
                                paddingValues = paddingValues
                            )
                        }
                        is FeedView.FeedGrid -> {
                            FeedGridLoading(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                                paddingValues = paddingValues
                            )
                        }
                    }
                }
                pagingItems.isFailure -> {
                    FeedFailure(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .clickableWithoutRipple { pagingItems.retry() },
                        onCheckConnectivityClick = {
                            if (Build.VERSION.SDK_INT >= 29) {
                                settingsPanelContract.launch(
                                    Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
                                )
                            }
                        }
                    )
                }
                else -> {
                    FeedContent(
                        currentFeedView = currentFeedView,
                        lazyListState = lazyListState,
                        lazyStaggeredGridState = lazyStaggeredGridState,
                        pagingItems = pagingItems,
                        onMovieClick = onNavigateToDetails,
                        contentPadding = paddingValues,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}