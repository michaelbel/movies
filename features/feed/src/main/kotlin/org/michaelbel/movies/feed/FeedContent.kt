package org.michaelbel.movies.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.navigation.NavGraph

@Composable
fun FeedContent(
    navController: NavController,
    onAppUpdateClicked: () -> Unit
) {
    val viewModel: FeedViewModel = hiltViewModel()
    val movies: LazyPagingItems<MovieData> = viewModel.moviesStateFlow
        .collectAsLazyPagingItems()
    val snackbarUpdateVisibleState: Boolean by rememberUpdatedState(
        viewModel.updateAvailableMessage
    )

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val listState: LazyListState = rememberLazyListState()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    val onScrollToTop: () -> Unit = {
        coroutineScope.launch {
            listState.animateScrollToItem(0)
        }
    }

    Scaffold(
        topBar = {
            Toolbar(
                onScrollToTopAction = onScrollToTop,
                onSettingsIconClick = {
                    navController.navigate(NavGraph.Settings.route)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues: PaddingValues ->
        Content(
            paddingValues = paddingValues,
            navController = navController,
            listState = listState,
            movies = movies
        )
    }
}

@Composable
private fun Toolbar(
    onScrollToTopAction: () -> Unit,
    onSettingsIconClick: () -> Unit
) {
    SmallTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.feed_title)
            )
        },
        modifier = Modifier
            .statusBarsPadding()
            .clickable { onScrollToTopAction() },
        actions = {
            IconButton(
                onClick = onSettingsIconClick
            ) {
                Image(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    )
}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    navController: NavController,
    listState: LazyListState,
    movies: LazyPagingItems<MovieData>
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        LazyColumn(
            state = listState
        ) {
            items(movies) { movieItem ->
                movieItem?.let { movie ->
                    MovieBox(
                        movie = movie,
                        onClick = {
                            navController.navigate("${NavGraph.Movie.route}/${movie.id}")
                        },
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
            movies.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item { LoadingListItem() }
                    }
                    is LoadState.Error -> {
                        item { ErrorListItem(onRetryClick = { retry() }) }
                    }
                    else -> { /* not implemented */ }
                }
            }
        }
    }
}