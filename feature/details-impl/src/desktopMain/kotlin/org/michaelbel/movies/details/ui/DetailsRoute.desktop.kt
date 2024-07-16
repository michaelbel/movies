package org.michaelbel.movies.details.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.koinInject
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.ui.ktx.collectAsStateCommon

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    interactor: Interactor = koinInject<Interactor>(),
    viewModel: DetailsViewModel = viewModel<DetailsViewModel> { DetailsViewModel(createSavedStateHandle(), interactor) }
) {
    val detailsState by viewModel.detailsState.collectAsStateCommon()

    DetailsScreenContent(
        onBackClick = onBackClick,
        onNavigateToGallery = onNavigateToGallery,
        detailsState = detailsState,
        modifier = modifier
    )
}