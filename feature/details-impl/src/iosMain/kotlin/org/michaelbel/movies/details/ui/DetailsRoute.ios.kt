package org.michaelbel.movies.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    //interactor: Interactor = koinInject<Interactor>(),
    //viewModel: DetailsViewModel = viewModel<DetailsViewModel> { DetailsViewModel(createSavedStateHandle(), interactor) }
) {
    /*val detailsState by viewModel.detailsState.collectAsStateCommon()

    DetailsScreenContent(
        onBackClick = onBackClick,
        onNavigateToGallery = onNavigateToGallery,
        detailsState = detailsState,
        modifier = modifier
    )*/
}