package org.michaelbel.movies.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.placeholder
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun FeedLoading(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = paddingValues
    ) {
        items(MovieResponse.DEFAULT_PAGE_SIZE) {
            FeedMovieBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    )
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.inversePrimary,
                        shape = MaterialTheme.shapes.small,
                        highlight = PlaceholderHighlight.fade()
                    ),
                movie = MovieDb.Empty
            )
        }
    }
}

@Composable
@DevicePreviews
private fun FeedLoadingPreview() {
    MoviesTheme {
        FeedLoading(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
    }
}