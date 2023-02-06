package org.michaelbel.movies.details.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.details.R
import org.michaelbel.movies.entities.MovieDetailsData
import org.michaelbel.movies.entities.lce.ScreenState

internal val ScreenState.Content<*>.movie: MovieDetailsData
    get() = data as MovieDetailsData

internal val ScreenState.toolbarTitle: String
    @Composable get() = when (this) {
        is ScreenState.Loading -> stringResource(R.string.details_title)
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> stringResource(R.string.details_title)
    }