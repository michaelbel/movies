package org.michaelbel.movies.feed.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun FeedMovieBox(
    modifier: Modifier = Modifier,
    movie: MovieData
) {
    val context: Context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(movie.backdropPath)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .height(220.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            maxLines = 10,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private class MoviePreviewParameterProvider: PreviewParameterProvider<MovieData> {
    override val values: Sequence<MovieData> = sequenceOf(
        MovieData(
            id = 438148,
            overview = "",
            posterPath = "/19GXuePqcZSPD5JgT9MeVdeu9Tc.jpg",
            backdropPath = "https://image.tmdb.org/t/p/w500//nmGWzTLMXy9x7mKd8NKPLmHtWGa.jpg",
            releaseDate = "2022-06-29",
            title = "Миньоны: Грювитация",
            voteAverage = 7.6F,
            genreIds = listOf(16, 12, 35, 14)
        )
    )
}

@Composable
@DevicePreviews
private fun MovieBoxPreview(
    @PreviewParameter(MoviePreviewParameterProvider::class) movie: MovieData
) {
    MoviesTheme {
        FeedMovieBox(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 4.dp,
                    end = 16.dp,
                    bottom = 4.dp
                )
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inversePrimary),
            movie = movie
        )
    }
}