package org.michaelbel.movies.fave

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.plus
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.fave.event.FaveEvent
import org.michaelbel.movies.fave.event.FaveEventManager
import org.michaelbel.movies.fave.intent.FaveIntent
import org.michaelbel.movies.ui.ObserveAsEvents
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.compose.page.FeedEmpty
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.isFailure
import org.michaelbel.movies.ui.isLoading
import org.michaelbel.movies.ui.isNavigationRail
import org.michaelbel.movies.ui.refreshThrowable
import org.michaelbel.movies.ui.rememberConnectivityClickHandler

@Composable
fun FaveScreen(
    viewModel: FaveViewModel = koinViewModel()
) {
    val state by viewModel.stateFlow.collectAsStateCommon()
    val pagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    ObserveAsEvents(
        flow = viewModel.eventFlow,
        key1 = lazyStaggeredGridState
    ) { event ->
        when (event) {
            is FaveEvent.ScrollToTop -> {
                scope.launch { lazyStaggeredGridState.animateScrollToItem(0) }
            }
            else -> Unit
        }
    }

    ObserveAsEvents(flow = FaveEventManager.eventFlow) { event ->
        when (event) {
            FaveEvent.ReselectFave -> viewModel.dispatch(FaveIntent.ScrollToTop)
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(WindowInsetsSides.Horizontal)
    ) { innerPadding ->
        when {
            pagingItems.isLoading -> {
                PageLoading(
                    feedView = state.feedView,
                    contentPadding = innerPadding + PaddingValues(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                        bottom = if (isNavigationRail) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 16.dp
                    )
                )
            }
            pagingItems.isFailure -> {
                when {
                    pagingItems.refreshThrowable is PageEmptyException -> {
                        FeedEmpty(
                            contentPadding = innerPadding + PaddingValues(
                                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                                bottom = if (isNavigationRail) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 16.dp
                            )
                        )
                    }
                    else -> {
                        PageFailure(
                            onClick = pagingItems::retry,
                            contentPadding = innerPadding + PaddingValues(
                                top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                                bottom = if (isNavigationRail) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 16.dp
                            ),
                            isButtonVisible = state.isPageFailureButtonVisible,
                            onButtonClick = rememberConnectivityClickHandler()
                        )
                    }
                }
            }
            else -> {
                PageContent(
                    feedView = state.feedView,
                    pagingItems = pagingItems,
                    lazyStaggeredGridState = lazyStaggeredGridState,
                    onMovieClick = { pagingKey, movieId ->
                        viewModel.dispatch(FaveIntent.MovieDetailsClick(pagingKey, movieId))
                    },
                    contentPadding = innerPadding + PaddingValues(
                        top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
                        bottom = if (isNavigationRail) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() else 16.dp
                    )
                )
            }
        }
    }
}
