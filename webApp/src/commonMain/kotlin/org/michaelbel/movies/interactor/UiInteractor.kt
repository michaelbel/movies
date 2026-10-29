package org.michaelbel.movies.interactor

import androidx.compose.runtime.Composable
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.ui.appicon.IconAlias

interface UiInteractor {

    val isLanguageFeatureEnabled: Boolean

    val isThemeFeatureEnabled: Boolean

    val isFeedViewFeatureEnabled: Boolean

    val isFaveFeatureEnabled: Boolean

    val isMovieListFeatureEnabled: Boolean

    val isGenderFeatureEnabled: Boolean

    val isDynamicColorsFeatureEnabled: Boolean

    val defaultDynamicColorsEnabled: Boolean

    val isPaletteColorsFeatureEnabled: Boolean

    val isNotificationsFeatureEnabled: Boolean

    val isDoNotDisturbFeatureEnabled: Boolean

    val isBatteryOptimizationFeatureEnabled: Boolean

    val isBiometricFeatureEnabled: Boolean

    val isWidgetFeatureEnabled: Boolean

    val isTileFeatureEnabled: Boolean

    val isAppIconFeatureEnabled: Boolean

    val isAppOpenByDefaultFeatureEnabled: Boolean

    val isScreenshotFeatureEnabled: Boolean

    val isGithubFeatureEnabled: Boolean

    val isTelegramFeatureEnabled: Boolean

    val isReviewAppFeatureEnabled: Boolean

    val isUpdateAppFeatureEnabled: Boolean

    val isAboutFeatureEnabled: Boolean

    val isSettingsResetFeatureEnabled: Boolean

    val isDebugDialogFeatureEnabled: Boolean

    val isFeedAuthIconFeatureEnabled: Boolean

    val isFeedVoiceInputFeatureEnabled: Boolean

    val isDetailsFavoriteFeatureEnabled: Boolean

    val isDetailsGalleryFeatureEnabled: Boolean

    val isDetailsShareFeatureEnabled: Boolean

    val isPageFailureButtonVisible: Boolean

    @Composable
    fun navigateToAppNotificationSettings(): () -> Unit

    @Composable
    fun navigateToDoNotDisturbSettings(): () -> Unit

    val isIgnoringBatteryOptimizations: Boolean

    @Composable
    fun requestIgnoreBatteryOptimizations(): () -> Unit

    @Composable
    fun navigateToBatteryOptimizationSettings(): () -> Unit

    @Composable
    fun navigateToAppSettings(): () -> Unit

    @Composable
    fun navigateToAppOpenByDefaultSettings(): () -> Unit

    @Composable
    fun navigateToDeveloperSettings(): () -> Unit

    @Composable
    fun rememberCopyToClipboardHandler(): (String) -> Unit

    @Composable
    fun rememberPostNotificationsPermissionHandler(
        enabled: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ): () -> Unit

    val enabledIcon: IconAlias

    fun setIcon(iconAlias: IconAlias)

    val grammaticalGender: SealedString

    fun setGrammaticalGender(grammaticalGender: Int)
}
