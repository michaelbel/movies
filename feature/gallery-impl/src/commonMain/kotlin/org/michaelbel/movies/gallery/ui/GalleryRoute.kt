package org.michaelbel.movies.gallery.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.gallery.GalleryViewModel

@Composable
expect fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = koinViewModel()
)