package org.michaelbel.movies.settings.model

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

internal actual val isNavigationIconVisible: Boolean
    get() = true

internal actual val isLanguageFeatureEnabled: Boolean
    get() = false

internal actual val isThemeFeatureEnabled: Boolean
    get() = true

internal actual val isFeedViewFeatureEnabled: Boolean
    get() = true

internal actual val isMovieListFeatureEnabled: Boolean
    get() = true

internal actual val isGenderFeatureEnabled: Boolean
    get() = false

internal actual val isDynamicColorsFeatureEnabled: Boolean
    get() = false

internal actual val isPaletteColorsFeatureEnabled: Boolean
    get() = false

internal actual val isNotificationsFeatureEnabled: Boolean
    get() = false

internal actual val isBiometricFeatureEnabled: Boolean
    get() = false

internal actual val isWidgetFeatureEnabled: Boolean
    get() = false

internal actual val isTileFeatureEnabled: Boolean
    get() = false

internal actual val isAppIconFeatureEnabled: Boolean
    get() = false

internal actual val isScreenshotFeatureEnabled: Boolean
    get() = false

internal actual val isGithubFeatureEnabled: Boolean
    get() = true

internal actual val isReviewAppFeatureEnabled: Boolean
    get() = false

internal actual val isUpdateAppFeatureEnabled: Boolean
    get() = false

internal actual val isAboutFeatureEnabled: Boolean
    get() = true

internal actual val settingsWindowInsets: WindowInsets
    get() = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)

internal actual val bottomBarModifier: Modifier
    get() = Modifier