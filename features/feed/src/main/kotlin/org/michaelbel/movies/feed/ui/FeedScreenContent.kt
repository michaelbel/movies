package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.domain.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.feed.R
import org.michaelbel.movies.feed.ktx.isFailure
import org.michaelbel.movies.feed.ktx.isLoading
import org.michaelbel.movies.feed.ktx.throwable
import org.michaelbel.movies.navigation.NavGraph

@Composable
fun FeedScreenContent(
    navController: NavController
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val listState: LazyListState = rememberLazyListState()
    val viewModel: FeedViewModel = hiltViewModel()
    val pagingItems: LazyPagingItems<MovieData> = viewModel.pagingItems.collectAsLazyPagingItems()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    val onScrollToTop: () -> Unit = {
        scope.launch {
            listState.animateScrollToItem(0)
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

    Scaffold(
        topBar = {
            FeedToolbar(
                modifier = Modifier
                    .statusBarsPadding()
                    .clickable { onScrollToTop() },
                onNavigationIconClick = {
                    navController.navigate(NavGraph.Settings.route)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues: PaddingValues ->
        when {
            pagingItems.isLoading -> {
                FeedLoading(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
            pagingItems.isFailure -> {
                FeedFailure(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
            else -> {
                FeedContent(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    listState = listState,
                    pagingItems = pagingItems,
                    onMovieClick = { movieId: Int ->
                        navController.navigate("${NavGraph.Movie.route}/$movieId")
                    }
                )
            }
        }
    }
}