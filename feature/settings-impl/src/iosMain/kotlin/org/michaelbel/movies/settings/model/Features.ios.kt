package org.michaelbel.movies.settings.model

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.dp

internal actual val isDynamicColorsFeatureEnabled: Boolean
    get() = false

internal actual val isPaletteColorsFeatureEnabled: Boolean
    get() = false

internal actual val settingsWindowInsets: WindowInsets
    get() = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)