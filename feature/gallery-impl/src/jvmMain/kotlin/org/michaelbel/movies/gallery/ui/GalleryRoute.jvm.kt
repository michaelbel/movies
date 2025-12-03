package org.michaelbel.movies.gallery.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.gallery.GalleryViewModel

@Composable
actual fun GalleryScreen(
    destination: org.michaelbel.movies.ui.navigation.GalleryDestination,
    viewModel: GalleryViewModel
) {
    Text(
        text = "Gallery",
        modifier = Modifier
    )
}