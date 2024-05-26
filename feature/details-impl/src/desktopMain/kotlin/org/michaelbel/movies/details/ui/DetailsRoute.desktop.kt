package org.michaelbel.movies.details.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.koinInject
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.interactor.Interactor

@Composable
fun DetailsRoute(
    onBackClick: () -> Unit,
    onNavigateToGallery: (Int) -> Unit,
    modifier: Modifier = Modifier,
    interactor: Interactor = koinInject<Interactor>(),
    viewModel: DetailsViewModel = viewModel<DetailsViewModel> { DetailsViewModel(createSavedStateHandle(), interactor) }
) {
    Text(
        text = "Details",
        modifier = Modifier.clickable { onBackClick() }
    )
}