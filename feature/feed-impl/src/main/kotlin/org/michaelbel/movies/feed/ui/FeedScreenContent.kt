@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.feed.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.feed.ktx.titleText
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.persistence.database.entity.AccountDb
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.ui.compose.NotificationBottomSheet
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.isFailure
import org.michaelbel.movies.ui.ktx.isLoading
import org.michaelbel.movies.ui.ktx.refreshThrowable
import org.michaelbel.movies.ui.R as UiR

@Composable
fun FeedRoute(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val account by viewModel.account.collectAsStateWithLifecycle()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val notificationsPermissionRequired by viewModel.notificationsPermissionRequired.collectAsStateWithLifecycle()
    val networkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val isAuthFailureSnackbarShowed = viewModel.isAuthFailureSnackbarShowed

    FeedScreenContent(
        pagingItems = pagingItems,
        account = account.orEmpty,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        currentMovieList = currentMovieList,
        notificationsPermissionRequired = notificationsPermissionRequired,
        isAuthFailureSnackbarShowed = isAuthFailureSnackbarShowed,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToAuth = onNavigateToAuth,
        onNavigateToAccount = onNavigateToAccount,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails,
        onNotificationBottomSheetHideClick = viewModel::onNotificationBottomSheetHide,
        onSnackbarDismissed = viewModel::onSnackbarDismissed,
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
    isAuthFailureSnackbarShowed: Boolean,
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    onNotificationBottomSheetHideClick: () -> Unit,
    onSnackbarDismissed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val snackbarHostState = remember { SnackbarHostState() }
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
        scope.launch { lazyListState.animateScrollToItem(0) }
        scope.launch { lazyGridState.animateScrollToItem(0) }
        scope.launch { lazyStaggeredGridState.animateScrollToItem(0) }
    }

    val onShowSnackbar: (String, SnackbarDuration) -> Unit = { message, snackbarDuration ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            val snackbarResult = snackbarHostState.showSnackbar(
                message = message,
                duration = snackbarDuration
            )
            if (snackbarResult == SnackbarResult.Dismissed) {
                onSnackbarDismissed()
            }
        }
    }

    if (pagingItems.isFailure && pagingItems.refreshThrowable is ApiKeyNotNullException) {
        onShowSnackbar(stringResource(UiR.string.error_api_key_null), SnackbarDuration.Long)
    }

    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.refreshThrowable is UnknownHostException) {
        pagingItems.retry()
    }

    if (notificationsPermissionRequired) {
        onNotificationBottomSheetShow()
    }

    var isBottomSheetExpanded by remember { mutableStateOf(false) }
    isBottomSheetExpanded = notificationBottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.Expanded

    if (isAuthFailureSnackbarShowed) {
        onShowSnackbar(stringResource(R.string.feed_auth_failure), SnackbarDuration.Short)
    }

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
        val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            modifier = modifier
                .padding(bottomSheetInnerPadding)
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
            topBar = {
                FeedToolbar(
                    title = currentMovieList.titleText,
                    modifier = Modifier.clickableWithoutRipple(onScrollToTop),
                    account = account,
                    onSearchIconClick = onNavigateToSearch,
                    onAuthIconClick = {
                        when {
                            isTmdbApiKeyEmpty -> onShowSnackbar(context.getString(UiR.string.error_api_key_null), SnackbarDuration.Long)
                            else -> onNavigateToAuth()
                        }
                    },
                    onAccountIconClick = onNavigateToAccount,
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
                    if (pagingItems.refreshThrowable is PageEmptyException) {
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