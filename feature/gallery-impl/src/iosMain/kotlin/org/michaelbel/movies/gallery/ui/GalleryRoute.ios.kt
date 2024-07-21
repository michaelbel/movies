package org.michaelbel.movies.gallery.ui

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GalleryRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: GalleryViewModel = koinInject<GalleryViewModel>()
) {
    Text(
        text = "Gallery",
        modifier = Modifier.clickable { onBackClick() }
    )
}