package org.michaelbel.movies.details.ui

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.movie
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.entities.lce.ScreenState

@Composable
internal fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState: ScreenState by viewModel.detailsState.collectAsStateWithLifecycle()

    DetailsScreenContent(
        onBackClick = onBackClick,
        modifier = modifier,
        detailsState = detailsState
    )
}

@Composable
internal fun DetailsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    detailsState: ScreenState
) {
    val context: Context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailsToolbar(
                modifier = Modifier
                    .statusBarsPadding(),
                onNavigationIconClick = onBackClick,
                movieTitle = detailsState.toolbarTitle(context)
            )
        }
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