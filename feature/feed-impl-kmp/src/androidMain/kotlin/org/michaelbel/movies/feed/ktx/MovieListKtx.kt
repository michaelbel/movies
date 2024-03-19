package org.michaelbel.movies.feed.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.feed_impl_kmp.R

actual val MovieList.titleText: String
    @Composable get() = when (this) {
        is MovieList.NowPlaying -> stringResource(R.string.feed_title_now_playing)
        is MovieList.Popular -> stringResource(R.string.feed_title_popular)
        is MovieList.TopRated -> stringResource(R.string.feed_title_top_rated)
        is MovieList.Upcoming -> stringResource(R.string.feed_title_upcoming)
    }