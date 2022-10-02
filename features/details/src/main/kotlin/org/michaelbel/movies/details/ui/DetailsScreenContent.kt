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
import androidx.navigation.NavController
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.details.ktx.toolbarTitle
import org.michaelbel.movies.details.model.DetailsState
import org.michaelbel.movies.ui.MoviesTheme

@Composable
fun DetailsScreenContent(
    navController: NavController
) {
    val context: Context = LocalContext.current
    val viewModel: DetailsViewModel = hiltViewModel()
    val state: DetailsState by viewModel.state.collectAsState(DetailsState.Loading)

    Scaffold(
        topBar = {
            DetailsToolbar(
                modifier = Modifier
                    .statusBarsPadding(),
                navController = navController,
                movieTitle = state.toolbarTitle(context)
            )
        }
    ) { paddingValues: PaddingValues ->
        when (state) {
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
                    movie = (state as DetailsState.Content).movie,
                    adRequest = viewModel.adRequest
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

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DetailsScreenContentPreview() {
    MoviesTheme {
        DetailsScreenContent(
            navController = NavController(LocalContext.current)
        )
    }
}