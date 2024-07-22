@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.details.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.movieUrl
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.network.config.ScreenState
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

@Composable
internal fun DetailsScreenContent(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    detailsState: ScreenState,
    modifier: Modifier = Modifier
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DetailsToolbar(
                movieTitle = detailsState.toolbarTitle,
                movieUrl = detailsState.movieUrl,
                onNavigationIconClick = onBackClick,
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
        when (detailsState) {
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
                    movie = detailsState.movie,
                    onNavigateToGallery = onNavigateToGallery
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