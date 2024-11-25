package org.michaelbel.movies.settings.model

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.Modifier

internal expect val isNavigationIconVisible: Boolean
internal expect val isLanguageFeatureEnabled: Boolean
internal expect val isThemeFeatureEnabled: Boolean
internal expect val isFeedViewFeatureEnabled: Boolean
internal expect val isMovieListFeatureEnabled: Boolean
internal expect val isGenderFeatureEnabled: Boolean
internal expect val isDynamicColorsFeatureEnabled: Boolean
internal expect val isPaletteColorsFeatureEnabled: Boolean
internal expect val isNotificationsFeatureEnabled: Boolean
internal expect val isBiometricFeatureEnabled: Boolean
internal expect val isWidgetFeatureEnabled: Boolean
internal expect val isTileFeatureEnabled: Boolean
internal expect val isAppIconFeatureEnabled: Boolean
internal expect val isScreenshotFeatureEnabled: Boolean
internal expect val isGithubFeatureEnabled: Boolean
internal expect val isReviewAppFeatureEnabled: Boolean
internal expect val isUpdateAppFeatureEnabled: Boolean
internal expect val isAboutFeatureEnabled: Boolean
internal expect val settingsWindowInsets: WindowInsets
internal expect val bottomBarModifier: Modifier