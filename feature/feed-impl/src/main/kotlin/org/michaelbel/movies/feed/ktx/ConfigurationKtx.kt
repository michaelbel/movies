package org.michaelbel.movies.feed.ktx

import androidx.compose.runtime.Composable
import org.michaelbel.movies.ui.ktx.isPortrait

private const val FEED_GRID_PORTRAIT_COLUMNS_COUNT = 2
private const val FEED_GRID_LANDSCAPE_COLUMNS_COUNT = 4

val gridColumnsCount: Int
    @Composable get() = if (isPortrait) FEED_GRID_PORTRAIT_COLUMNS_COUNT else FEED_GRID_LANDSCAPE_COLUMNS_COUNT