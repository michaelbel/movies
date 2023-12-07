package org.michaelbel.movies.feed.ui

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.feed.ktx.titleText
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.ui.compose.NotificationBottomSheet
import org.michaelbel.movies.ui.compose.PageContent
import org.michaelbel.movies.ui.compose.PageFailure
import org.michaelbel.movies.ui.compose.PageLoading
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.isFailure
import org.michaelbel.movies.ui.ktx.isLoading
import org.michaelbel.movies.ui.ktx.throwable
import org.michaelbel.movies.ui.R as UiR

@Composable
fun FeedRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
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
        onNavigateToSearch = onNavigateToSearch,
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
    onNavigateToSearch: () -> Unit,
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
    val lazyGridState: LazyGridState = rememberLazyGridState()
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
            lazyGridState.animateScrollToItem(0)
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
        onShowSnackbar(stringResource(UiR.string.error_api_key_null))
    }

    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.throwable is UnknownHostException) {
        pagingItems.retry()
    }

    if (notificationsPermissionRequired) {
        onNotificationBottomSheetShow()
    }

    var isBottomSheetExpanded: Boolean by remember { mutableStateOf(false) }
    isBottomSheetExpanded = notificationBottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded

    BackHandler(isBottomSheetExpanded, onNotificationBottomSheetHide)

    BottomSheetScaffold(
        sheetContent = {
            NotificationBottomSheet(
                modifier = Modifier.fillMaxWidth(),
                onBottomSheetHide = onNotificationBottomSheetHide
            )
        },
        scaffoldState = notificationBottomSheetScaffoldState,
        sheetPeekHeight = 0.dp
    ) { bottomSheetInnerPadding ->
        val topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = modifier
                .padding(bottomSheetInnerPadding)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                FeedToolbar(
                    title = currentMovieList.titleText,
                    modifier = Modifier.clickableWithoutRipple(onScrollToTop),
                    account = account,
                    isUpdateIconVisible = isUpdateIconVisible,
                    onSearchIconClick = onNavigateToSearch,
                    onAuthIconClick = {
                        when {
                            isTmdbApiKeyEmpty -> onShowSnackbar(context.getString(UiR.string.error_api_key_null))
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
        ) { innerPadding ->
            when {
                pagingItems.isLoading -> {
                    PageLoading(
                        feedView = currentFeedView,
                        modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                        paddingValues = innerPadding
                    )
                }
                pagingItems.isFailure -> {
                    if (pagingItems.throwable is PageEmptyException) {
                        FeedEmpty(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                        )
                    } else {
                        PageFailure(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                                .clickableWithoutRipple(pagingItems::retry)
                        )
                    }
                }
                else -> {
                    PageContent(
                        feedView = currentFeedView,
                        lazyListState = lazyListState,
                        lazyGridState = lazyGridState,
                        lazyStaggeredGridState = lazyStaggeredGridState,
                        pagingItems = pagingItems,
                        onMovieClick = onNavigateToDetails,
                        contentPadding = innerPadding,
                        modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
                    )
                }
            }
        }
    }
}