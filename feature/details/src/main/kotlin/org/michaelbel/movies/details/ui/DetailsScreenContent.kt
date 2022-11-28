package org.michaelbel.movies.details.ui

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.net.UnknownHostException
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.entities.lce.ScreenState
import org.michaelbel.movies.entities.lce.ktx.isFailure
import org.michaelbel.movies.entities.lce.ktx.throwable
import org.michaelbel.movies.network.connectivity.NetworkStatus

@Composable
internal fun DetailsRoute(
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
internal fun DetailsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    detailsState: ScreenState,
    networkStatus: NetworkStatus,
    onRetry: () -> Unit
) {
    val context: Context = LocalContext.current

    if (networkStatus == NetworkStatus.Available && detailsState.isFailure && detailsState.throwable is UnknownHostException) {
        onRetry()
    }

    Scaffold(
        modifier = modifier
            .testTag("DetailsContent"),
        topBar = {
            DetailsToolbar(
                modifier = Modifier
                    .statusBarsPadding(),
                onNavigationIconClick = onBackClick,
                movieTitle = detailsState.toolbarTitle(context)
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