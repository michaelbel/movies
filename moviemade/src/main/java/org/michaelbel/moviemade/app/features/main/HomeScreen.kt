package org.michaelbel.moviemade.app.features.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.michaelbel.moviemade.data.model.MovieResponse

@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val listState: LazyListState = rememberLazyListState()
    val movies: LazyPagingItems<MovieResponse> = viewModel.moviesStateFlow.collectAsLazyPagingItems()
    val isRefreshing: Boolean by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = { Toolbar() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SmallTopAppBar(
                title = {
                    Text("Популярное")
                },
                actions = {
                    IconButton(onClick = { /*viewModel.openFavoritesScreen()*/ }) {
                        Image(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                    IconButton(onClick = { /*viewModel.openSettingsScreen()*/ }) {
                        Image(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                        )
                    }
                }
            )
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { movies.refresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        scale = true,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                    )
                }
            ) {
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(minSize = 128.dp),
                    state = listState,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                ) {
                    items(movies) { movie ->
                        movie?.let {
                            MovieCard(
                                movie = it,
                                onClick = {
                                    viewModel.openDetailsScreen(it.id)

                                    navController.navigate(ROUTE_DETAILS) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            popUpTo(route) {
                                                saveState = true
                                            }
                                        }
                                        restoreState = true
                                        launchSingleTop = true
                                    }
                                          },
                                modifier = Modifier
                                    .padding(1.dp)
                            )
                        }
                    }
                    movies.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> {
                                item { LoadingItem() }
                            }
                            is LoadState.Error -> {
                                item { ErrorItem(onRetryClick = { retry() }) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Toolbar() {
    SmallTopAppBar(
        title = { Text(text = "Compose Design") },
        modifier = Modifier.systemBarsPadding()
    )
}

@Composable
private fun Content(
    navController: NavController,
    isRefreshing: Boolean
) {

}