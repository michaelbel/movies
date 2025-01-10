package org.michaelbel.movies.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.details.DetailsViewModel

@Composable
expect fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = koinViewModel()
)