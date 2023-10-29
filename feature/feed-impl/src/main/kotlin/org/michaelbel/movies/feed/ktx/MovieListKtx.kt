package org.michaelbel.movies.feed.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty
import org.michaelbel.movies.feed_impl.R
import org.michaelbel.movies.persistence.database.entity.MovieDb

internal val MovieList.nameOrLocalList: String
    get() = if (isTmdbApiKeyEmpty) MovieDb.MOVIES_LOCAL_LIST else name

internal val MovieList.titleText: String
    @Composable get() = when (this) {
        is MovieList.NowPlaying -> stringResource(R.string.feed_title_now_playing)
        is MovieList.Popular -> stringResource(R.string.feed_title_popular)
        is MovieList.TopRated -> stringResource(R.string.feed_title_top_rated)
        is MovieList.Upcoming -> stringResource(R.string.feed_title_upcoming)
    }