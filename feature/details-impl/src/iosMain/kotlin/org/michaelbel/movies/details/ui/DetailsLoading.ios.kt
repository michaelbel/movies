package org.michaelbel.movies.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun DetailsLoading(
    modifier: Modifier = Modifier
) {
    DetailsContent(
        modifier = modifier,
        movie = MoviePojo.Empty,
        onNavigateToGallery = {},
        placeholder = true
    )
}

@Composable
/*@DevicePreviews*/
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
/*@Preview*/
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