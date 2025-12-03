@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
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
import org.michaelbel.movies.ui.strings.MoviesStrings

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
private fun DetailsScreenContent(
    state: DetailsModel,
    dispatch: (DetailsIntent) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val scope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current
    val snackbarHostState = remember { SnackbarHostState() }
    val copiedText = stringResource(MoviesStrings.details_url_copied)

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DetailsToolbar(
                movieTitle = state.detailsState.toolbarTitle,
                movieUrl = state.detailsState.movieUrl,
                onNavigationIconClick = { dispatch(DetailsIntent.BackClick) },
                onShareClick = { url ->
                    clipboardManager.setText(AnnotatedString(url))
                    onShowSnackbar(copiedText)
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