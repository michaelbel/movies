package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.list.MovieList

class MovieListPreviewParameterProvider: PreviewParameterProvider<MovieList> {
    override val values: Sequence<MovieList> = sequenceOf(
        MovieList.NowPlaying,
        MovieList.Popular,
        MovieList.TopRated,
        MovieList.Upcoming
    )
}