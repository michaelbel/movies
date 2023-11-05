package org.michaelbel.movies.details.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.url

internal val ScreenState.Content<*>.movie: MovieDb
    get() = data as MovieDb

internal val ScreenState.toolbarTitle: String
    @Composable get() = when (this) {
        is ScreenState.Loading -> stringResource(R.string.details_title)
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> stringResource(R.string.details_title)
    }

internal val ScreenState.movieUrl: String?
    get() = if (this is ScreenState.Content<*>) movie.url else null