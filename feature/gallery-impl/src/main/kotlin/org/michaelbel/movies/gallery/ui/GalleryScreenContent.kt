package org.michaelbel.movies.gallery.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import org.michaelbel.movies.gallery.GalleryViewModel

@Composable
fun GalleryRoute(
    modifier: Modifier = Modifier,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    GalleryScreenContent(
        modifier = modifier
    )
}

@Composable
private fun GalleryScreenContent(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { paddingValues ->
        AsyncImage(
            model = "",
            contentDescription = null,
            modifier = Modifier.padding(paddingValues),
            contentScale = ContentScale.Crop
        )
    }
}