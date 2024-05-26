package org.michaelbel.movies.feed.ktx

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.ui.strings.MoviesStrings

internal val MovieList.titleText: String
    @Composable get() = when (this) {
        is MovieList.NowPlaying -> stringResource(MoviesStrings.feed_title_now_playing)
        is MovieList.Popular -> stringResource(MoviesStrings.feed_title_popular)
        is MovieList.TopRated -> stringResource(MoviesStrings.feed_title_top_rated)
        is MovieList.Upcoming -> stringResource(MoviesStrings.feed_title_upcoming)
    }