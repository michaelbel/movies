package org.michaelbel.movies.interactor

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.ui.appicon.IconAlias

interface SettingsUiInteractor {

    val isNavigationIconVisible: Boolean

    val isLanguageFeatureEnabled: Boolean

    val isThemeFeatureEnabled: Boolean

    val isFeedViewFeatureEnabled: Boolean

    val isMovieListFeatureEnabled: Boolean

    val isGenderFeatureEnabled: Boolean

    val isDynamicColorsFeatureEnabled: Boolean

    val isPaletteColorsFeatureEnabled: Boolean

    val isNotificationsFeatureEnabled: Boolean

    val isBiometricFeatureEnabled: Boolean

    val isWidgetFeatureEnabled: Boolean

    val isTileFeatureEnabled: Boolean

    val isAppIconFeatureEnabled: Boolean

    val isScreenshotFeatureEnabled: Boolean

    val isGithubFeatureEnabled: Boolean

    val isReviewAppFeatureEnabled: Boolean

    val isUpdateAppFeatureEnabled: Boolean

    val isAboutFeatureEnabled: Boolean

    @get:Composable
    val settingsWindowInsets: WindowInsets

    val bottomBarModifier: Modifier

    @Composable
    fun navigateToAppNotificationSettings(): () -> Unit

    @Composable
    fun rememberPostNotificationsPermissionHandler(
        areNotificationsEnabled: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ): () -> Unit

    val enabledIcon: IconAlias

    fun setIcon(iconAlias: IconAlias)

    val grammaticalGender: SealedString

    fun setGrammaticalGender(grammaticalGender: Int)
}