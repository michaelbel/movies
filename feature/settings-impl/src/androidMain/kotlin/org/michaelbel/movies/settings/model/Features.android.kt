package org.michaelbel.movies.settings.model

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import com.google.android.material.color.DynamicColors
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets

internal actual val isDynamicColorsFeatureEnabled: Boolean
    get() = DynamicColors.isDynamicColorAvailable()

internal actual val isPaletteColorsFeatureEnabled: Boolean
    get() = DynamicColors.isDynamicColorAvailable()

internal actual val settingsWindowInsets: WindowInsets
    @Composable get() = displayCutoutWindowInsets