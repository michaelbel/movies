package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import org.michaelbel.movies.common.list.MovieList

actual class MovieListPreviewParameterProvider: PreviewParameterProvider<MovieList> {
    override val values = MovieList.VALUES.asSequence()
}