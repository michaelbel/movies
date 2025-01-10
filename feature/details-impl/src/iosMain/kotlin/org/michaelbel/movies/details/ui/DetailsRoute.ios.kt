package org.michaelbel.movies.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
actual fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier,
    viewModel: DetailsViewModel
) {
    val detailsState by viewModel.detailsState.collectAsStateCommon()

    DetailsScreenContent(
        onBackClick = onBackClick,
        onNavigateToGallery = onNavigateToGallery,
        detailsState = detailsState,
        modifier = modifier
    )
}