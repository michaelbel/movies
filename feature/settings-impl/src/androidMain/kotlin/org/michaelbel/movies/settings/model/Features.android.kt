package org.michaelbel.movies.settings.model

import android.os.Build
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import com.google.android.material.color.DynamicColors
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets

internal actual val isNavigationIconVisible: Boolean
    get() = false

internal actual val isLanguageFeatureEnabled: Boolean
    get() = true

internal actual val isThemeFeatureEnabled: Boolean
    get() = true

internal actual val isFeedViewFeatureEnabled: Boolean
    get() = true

internal actual val isMovieListFeatureEnabled: Boolean
    get() = true

internal actual val isGenderFeatureEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 34

internal actual val isDynamicColorsFeatureEnabled: Boolean
    get() = DynamicColors.isDynamicColorAvailable()

internal actual val isPaletteColorsFeatureEnabled: Boolean
    get() = DynamicColors.isDynamicColorAvailable()

internal actual val isNotificationsFeatureEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 33

internal actual val isBiometricFeatureEnabled: Boolean
    get() = true

internal actual val isWidgetFeatureEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 26

internal actual val isTileFeatureEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 33

internal actual val isAppIconFeatureEnabled: Boolean
    get() = true

internal actual val isScreenshotFeatureEnabled: Boolean
    get() = true

internal actual val isGithubFeatureEnabled: Boolean
    get() = true

internal actual val isReviewAppFeatureEnabled: Boolean
    get() = true

internal actual val isUpdateAppFeatureEnabled: Boolean
    get() = true

internal actual val isAboutFeatureEnabled: Boolean
    get() = true

internal actual val settingsWindowInsets: WindowInsets
    @Composable get() = displayCutoutWindowInsets