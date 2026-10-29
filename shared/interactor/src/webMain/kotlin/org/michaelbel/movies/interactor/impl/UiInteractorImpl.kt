package org.michaelbel.movies.interactor.impl

import androidx.compose.runtime.Composable
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.ui.appicon.IconAlias

class UiInteractorImpl: UiInteractor {

    override val isLanguageFeatureEnabled: Boolean = false

    override val isThemeFeatureEnabled: Boolean = true

    override val isFeedViewFeatureEnabled: Boolean = true

    override val isFaveFeatureEnabled: Boolean = true

    override val isMovieListFeatureEnabled: Boolean = true

    override val isGenderFeatureEnabled: Boolean = false

    override val isDynamicColorsFeatureEnabled: Boolean = false

    override val defaultDynamicColorsEnabled: Boolean = false

    override val isPaletteColorsFeatureEnabled: Boolean = true

    override val isNotificationsFeatureEnabled: Boolean = false

    override val isDoNotDisturbFeatureEnabled: Boolean = false

    override val isBatteryOptimizationFeatureEnabled: Boolean = false

    override val isBiometricFeatureEnabled: Boolean = false

    override val isWidgetFeatureEnabled: Boolean = false

    override val isTileFeatureEnabled: Boolean = false

    override val isAppIconFeatureEnabled: Boolean = false

    override val isAppOpenByDefaultFeatureEnabled: Boolean = false

    override val isScreenshotFeatureEnabled: Boolean = false

    override val isGithubFeatureEnabled: Boolean = true

    override val isTelegramFeatureEnabled: Boolean = true

    override val isReviewAppFeatureEnabled: Boolean = false

    override val isUpdateAppFeatureEnabled: Boolean = false

    override val isAboutFeatureEnabled: Boolean = true

    override val isSettingsResetFeatureEnabled: Boolean = true

    override val isDebugDialogFeatureEnabled: Boolean = false

    override val isFeedAuthIconFeatureEnabled: Boolean = true

    override val isFeedVoiceInputFeatureEnabled: Boolean = false

    override val isDetailsFavoriteFeatureEnabled: Boolean = true

    override val isDetailsGalleryFeatureEnabled: Boolean = false

    override val isDetailsShareFeatureEnabled: Boolean = false

    override val isPageFailureButtonVisible: Boolean = false

    @Composable
    override fun navigateToAppNotificationSettings(): () -> Unit = {}

    @Composable
    override fun navigateToDoNotDisturbSettings(): () -> Unit = {}

    override val isIgnoringBatteryOptimizations: Boolean = false

    @Composable
    override fun requestIgnoreBatteryOptimizations(): () -> Unit = {}

    @Composable
    override fun navigateToBatteryOptimizationSettings(): () -> Unit = {}

    @Composable
    override fun navigateToAppSettings(): () -> Unit = {}

    @Composable
    override fun navigateToAppOpenByDefaultSettings(): () -> Unit = {}

    @Composable
    override fun navigateToDeveloperSettings(): () -> Unit = {}

    @Composable
    override fun rememberCopyToClipboardHandler(): (String) -> Unit {
        return { _: String -> }
    }

    @Composable
    override fun rememberPostNotificationsPermissionHandler(
        enabled: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ): () -> Unit = {}

    override val enabledIcon: IconAlias = IconAlias.Red

    override fun setIcon(iconAlias: IconAlias) {}

    override val grammaticalGender: SealedString = GrammaticalGender.NotSpecified()

    override fun setGrammaticalGender(grammaticalGender: Int) {}
}
