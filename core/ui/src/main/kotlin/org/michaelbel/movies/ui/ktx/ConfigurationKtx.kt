package org.michaelbel.movies.ui.ktx

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

private const val FEED_GRID_PORTRAIT_COLUMNS_COUNT = 2
private const val FEED_GRID_LANDSCAPE_COLUMNS_COUNT = 4

val isPortrait: Boolean
    @Composable get() {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

val displayCutoutWindowInsets: WindowInsets
    @Composable get() = if (isPortrait) WindowInsets(0, 0, 0, 0) else WindowInsets.displayCutout

val gridColumnsCount: Int
    @Composable get() = if (isPortrait) FEED_GRID_PORTRAIT_COLUMNS_COUNT else FEED_GRID_LANDSCAPE_COLUMNS_COUNT