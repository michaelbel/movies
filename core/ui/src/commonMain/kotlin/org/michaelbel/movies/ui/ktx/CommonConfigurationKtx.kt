package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable

private const val FEED_GRID_PORTRAIT_COLUMNS_COUNT = 2
private const val FEED_GRID_LANDSCAPE_COLUMNS_COUNT = 4

internal val gridColumnsCount: Int
    @Composable get() = if (isPortrait) FEED_GRID_PORTRAIT_COLUMNS_COUNT else FEED_GRID_LANDSCAPE_COLUMNS_COUNT