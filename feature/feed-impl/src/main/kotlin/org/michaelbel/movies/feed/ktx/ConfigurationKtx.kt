package org.michaelbel.movies.feed.ktx

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

private const val FEED_GRID_PORTRAIT_COLUMNS_COUNT = 2
private const val FEED_GRID_LANDSCAPE_COLUMNS_COUNT = 4

val isPortrait: Boolean
    @Composable get() {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

val gridColumnsCount: Int
    @Composable get() = if (isPortrait) FEED_GRID_PORTRAIT_COLUMNS_COUNT else FEED_GRID_LANDSCAPE_COLUMNS_COUNT