package org.michaelbel.movies.feed.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.entities.MovieData

internal class MoviePreviewParameterProvider: PreviewParameterProvider<MovieData> {
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