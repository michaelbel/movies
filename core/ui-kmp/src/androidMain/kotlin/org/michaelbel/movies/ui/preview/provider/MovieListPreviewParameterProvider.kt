package org.michaelbel.movies.ui.preview.provider

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import org.michaelbel.movies.common.list.MovieList

class MovieListPreviewParameterProvider: CollectionPreviewParameterProvider<MovieList>(MovieList.VALUES)