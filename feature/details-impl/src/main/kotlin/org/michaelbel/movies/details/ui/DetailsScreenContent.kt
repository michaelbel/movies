package org.michaelbel.movies.details.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.entities.lce.ScreenState
import org.michaelbel.movies.entities.lce.ktx.isFailure
import org.michaelbel.movies.entities.lce.ktx.throwable
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.connectivity.ktx.isAvailable
import java.net.UnknownHostException

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState: ScreenState by viewModel.detailsState.collectAsStateWithLifecycle()
    val networkStatus: NetworkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    DetailsScreenContent(
        onBackClick = onBackClick,
        modifier = modifier,
        detailsState = detailsState,
        networkStatus = networkStatus,
        onRetry = viewModel::retry
    )
}

@Composable
private fun DetailsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    detailsState: ScreenState,
    networkStatus: NetworkStatus,
    onRetry: () -> Unit
) {
    if (networkStatus.isAvailable && detailsState.isFailure && detailsState.throwable is UnknownHostException) {
        onRetry()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailsToolbar(
                modifier = Modifier
                    .statusBarsPadding(),
                onNavigationIconClick = onBackClick,
                movieTitle = detailsState.toolbarTitle,
                movieUrl = detailsState.movieUrl
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { paddingValues: PaddingValues ->
        when (detailsState) {
            is ScreenState.Loading -> {
                DetailsLoading(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
            is ScreenState.Content<*> -> {
                DetailsContent(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    movie = detailsState.movie
                )
            }
            is ScreenState.Failure -> {
                DetailsFailure(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
        }
    }
}