package org.michaelbel.movies.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DetailsLoading(
    modifier: Modifier = Modifier
) {
    DetailsContent(
        modifier = modifier,
        movie = MovieDb.Empty,
        onNavigateToGallery = {},
        onGenerateColors = { _,_ -> },
        placeholder = true
    )
}

@Composable
@DevicePreviews
private fun DetailsLoadingPreview() {
    MoviesTheme {
        DetailsLoading(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun DetailsLoadingAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        DetailsLoading(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}