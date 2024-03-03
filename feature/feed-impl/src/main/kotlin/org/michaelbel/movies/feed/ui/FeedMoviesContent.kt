package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.isFailure
import org.michaelbel.movies.ui.ktx.isLoading
import org.michaelbel.movies.ui.ktx.refreshThrowable

@Composable
fun FeedMoviesContent(
    onNavigateToSearch: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (String, Int) -> Unit,
    onShowSnackbar: (String, SnackbarDuration) -> Unit,
    parentInnerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val account by viewModel.account.collectAsStateWithLifecycle()
    val currentFeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val currentMovieList by viewModel.currentMovieList.collectAsStateWithLifecycle()
    val isUpdateAvailable by rememberUpdatedState(viewModel.updateAvailableMessage)
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val lazyGridState = rememberLazyGridState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val onScrollToTop: () -> Unit = {
        scope.launch { lazyListState.animateScrollToItem(0) }
        scope.launch { lazyGridState.animateScrollToItem(0) }
        scope.launch { lazyStaggeredGridState.animateScrollToItem(0) }
    }

    when {
        pagingItems.isLoading -> {
            PageLoading(
                feedView = currentFeedView,
                modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets),
                paddingValues = parentInnerPadding
            )
        }
        pagingItems.isFailure -> {
            if (pagingItems.refreshThrowable is PageEmptyException) {
                FeedEmpty(
                    modifier = Modifier
                        .padding(parentInnerPadding)
                        .windowInsetsPadding(displayCutoutWindowInsets)
                        .fillMaxSize()
                )
            } else {
                PageFailure(
                    modifier = Modifier
                        .padding(parentInnerPadding)
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
                contentPadding = parentInnerPadding,
                modifier = modifier.windowInsetsPadding(displayCutoutWindowInsets)
            )
        }
    }
}