package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.settings_impl.R

internal val MovieList.title: String
    @Composable get() = when (this) {
        is MovieList.NowPlaying -> stringResource(R.string.settings_movie_list_now_playing)
        is MovieList.Popular -> stringResource(R.string.settings_movie_list_popular)
        is MovieList.TopRated -> stringResource(R.string.settings_movie_list_top_rated)
        is MovieList.Upcoming -> stringResource(R.string.settings_movie_list_upcoming)
    }