package org.michaelbel.movies.gallery.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun GalleryLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            trackColor = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Preview
@Composable
private fun GalleryLoadingPreview() {
    MoviesTheme {
        GalleryLoading(
            modifier = Modifier.fillMaxSize()
        )
    }
}