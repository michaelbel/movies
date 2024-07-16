@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import java.net.UnknownHostException
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed.ktx.titleText
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
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
internal fun FeedScreenContent(
    pagingItems: LazyPagingItems<MoviePojo>,
    account: AccountPojo,
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
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val snackbarHostState = remember { SnackbarHostState() }

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

    var modalDialog by remember { mutableStateOf(false) }
    modalDialog = notificationsPermissionRequired
    if (modalDialog) {
        NotificationBottomSheet(
            onDismissRequest = {
                modalDialog = false
                onNotificationBottomSheetHideClick()
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (isAuthFailureSnackbarShowed) {
        onShowSnackbar(stringResource(R.string.feed_auth_failure), SnackbarDuration.Short)
    }

    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            FeedToolbar(
                title = currentMovieList.titleText,
                modifier = Modifier.clickableWithoutRipple(onScrollToTop),
                account = account,
                isTmdbApiKeyEmpty = isTmdbApiKeyEmpty,
                isSearchIconVisible = true,
                onSearchIconClick = onNavigateToSearch,
                isAuthIconVisible = true,
                onAuthIconClick = onNavigateToAuth,
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