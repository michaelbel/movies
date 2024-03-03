package org.michaelbel.movies.feed.ktx

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocalMovies
import androidx.compose.material.icons.outlined.Slideshow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.feed.ui.common.FeedBottomTabs
import org.michaelbel.movies.feed_impl.R

internal val FeedBottomTabs.imageVector: ImageVector
    @Composable get() = when (this) {
        is FeedBottomTabs.Movies -> Icons.Outlined.LocalMovies
        is FeedBottomTabs.Series -> Icons.Outlined.Slideshow
    }

internal val FeedBottomTabs.label: String
    @Composable get() = when (this) {
        is FeedBottomTabs.Movies -> stringResource(R.string.feed_movies)
        is FeedBottomTabs.Series -> stringResource(R.string.feed_series)
    }