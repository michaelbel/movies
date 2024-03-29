package org.michaelbel.movies.settings.model

import android.os.Build

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
    get() = Build.VERSION.SDK_INT >= 31

internal actual val isNotificationsFeatureEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 33

internal actual val isBiometricFeatureEnabled: Boolean
    get() = true

internal actual val isWidgetFeatureEnabled: Boolean
    get() = true

internal actual val isTileFeatureEnabled: Boolean
    get() = Build.VERSION.SDK_INT >= 24

internal actual val isAppIconFeatureEnabled: Boolean
    get() = true

internal actual val isGithubFeatureEnabled: Boolean
    get() = true

internal actual val isReviewAppFeatureEnabled: Boolean
    get() = true

internal actual val isUpdateAppFeatureEnabled: Boolean
    get() = true

internal actual val isAboutFeatureEnabled: Boolean
    get() = true