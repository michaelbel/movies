package org.michaelbel.movies.details.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.net.UnknownHostException
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.connectivity.ktx.isAvailable
import org.michaelbel.movies.network.ktx.isFailure
import org.michaelbel.movies.network.ktx.throwable
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState: ScreenState by viewModel.detailsState.collectAsStateWithLifecycle()
    val networkStatus: NetworkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    DetailsScreenContent(
        onBackClick = onBackClick,
        onNavigateToGallery = onNavigateToGallery,
        detailsState = detailsState,
        networkStatus = networkStatus,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}

@Composable
private fun DetailsScreenContent(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    detailsState: ScreenState,
    networkStatus: NetworkStatus,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    if (networkStatus.isAvailable && detailsState.isFailure && detailsState.throwable is UnknownHostException) {
        onRetry()
    }

    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            DetailsToolbar(
                movieTitle = detailsState.toolbarTitle,
                movieUrl = detailsState.movieUrl,
                onNavigationIconClick = onBackClick,
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                modifier = Modifier.fillMaxWidth()
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        when (detailsState) {
            is ScreenState.Loading -> {
                DetailsLoading(
                    modifier = Modifier
                        .padding(innerPadding)
                        .windowInsetsPadding(displayCutoutWindowInsets)
                        .fillMaxSize()
                )
            }
            is ScreenState.Content<*> -> {
                DetailsContent(
                    modifier = Modifier
                        .padding(innerPadding)
                        .windowInsetsPadding(displayCutoutWindowInsets)
                        .fillMaxSize(),
                    movie = detailsState.movie,
                    onNavigateToGallery = onNavigateToGallery
                )
            }
            is ScreenState.Failure -> {
                DetailsFailure(
                    modifier = Modifier
                        .padding(innerPadding)
                        .windowInsetsPadding(displayCutoutWindowInsets)
                        .fillMaxSize()
                )
            }
        }
    }
}