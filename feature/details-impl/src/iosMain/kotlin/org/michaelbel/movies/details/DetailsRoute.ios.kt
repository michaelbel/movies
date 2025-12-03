@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.michaelbel.movies.details.intent.DetailsIntent
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.details.model.DetailsModel
import org.michaelbel.movies.details.ui.DetailsContent
import org.michaelbel.movies.details.ui.DetailsFailure
import org.michaelbel.movies.details.ui.DetailsLoading
import org.michaelbel.movies.details.ui.DetailsToolbar
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@Composable
actual fun DetailsScreen(
    destination: org.michaelbel.movies.ui.navigation.DetailsDestination,
    viewModel: DetailsViewModel
) {
    val state by viewModel.stateFlow.collectAsStateCommon()

    DetailsScreenContent(
        state = state,
        dispatch = viewModel::dispatch
    )
}

@Composable
internal fun DetailsScreenContent(
    state: DetailsModel,
    dispatch: (DetailsIntent) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DetailsToolbar(
                movieTitle = state.detailsState.toolbarTitle,
                movieUrl = state.detailsState.movieUrl,
                onNavigationIconClick = { dispatch(DetailsIntent.BackClick) },
                onShareClick = { url ->
                    val currentViewController = UIApplication.sharedApplication().keyWindow?.rootViewController
                    val activityViewController = UIActivityViewController(listOf(url), null)
                    currentViewController?.presentViewController(
                        viewControllerToPresent = activityViewController,
                        animated = true,
                        completion = null
                    )
                },
                topAppBarScrollBehavior = topAppBarScrollBehavior,
                modifier = Modifier.fillMaxWidth()
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        when (state.detailsState) {
            is ScreenState.Loading -> {
                DetailsLoading(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }
            is ScreenState.Content<*> -> {
                DetailsContent(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    movie = state.detailsState.movie,
                    onNavigateToGallery = { dispatch(DetailsIntent.GalleryClick) }
                )
            }
            is ScreenState.Failure -> {
                DetailsFailure(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }
        }
    }
}