package org.michaelbel.movies.details.ui

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.AdRequest
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.details.model.DetailsState
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val detailsState: DetailsState by viewModel.detailsState.collectAsStateWithLifecycle()

    DetailsScreenContent(
        onBackClick = onBackClick,
        modifier = modifier,
        detailsState = detailsState,
        adRequest = viewModel.adRequest
    )
}

@Composable
internal fun DetailsScreenContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    detailsState: DetailsState,
    adRequest: AdRequest
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
            is DetailsState.Loading -> {
                DetailsLoading(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
            is DetailsState.Content -> {
                DetailsContent(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    movie = detailsState.movie,
                    adRequest = adRequest
                )
            }
            is DetailsState.Failure -> {
                DetailsFailure(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
        }
    }
}