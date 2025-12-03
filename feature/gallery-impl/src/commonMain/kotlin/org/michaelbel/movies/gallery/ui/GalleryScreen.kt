package org.michaelbel.movies.gallery.ui

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.michaelbel.movies.ui.navigation.GalleryDestination
import org.michaelbel.movies.gallery.GalleryViewModel

@Composable
expect fun GalleryScreen(
    destination: GalleryDestination,
    viewModel: GalleryViewModel = koinViewModel(parameters = { parametersOf(destination) })
)