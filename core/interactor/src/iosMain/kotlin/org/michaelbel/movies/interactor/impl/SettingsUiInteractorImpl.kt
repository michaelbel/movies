package org.michaelbel.movies.interactor.impl

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.interactor.SettingsUiInteractor
import org.michaelbel.movies.ui.appicon.IconAlias

class SettingsUiInteractorImpl: SettingsUiInteractor {

    override val isNavigationIconVisible: Boolean
        get() = true

    override val isLanguageFeatureEnabled: Boolean
        get() = false

    override val isThemeFeatureEnabled: Boolean
        get() = true

    override val isFeedViewFeatureEnabled: Boolean
        get() = true

    override val isMovieListFeatureEnabled: Boolean
        get() = true

    override val isGenderFeatureEnabled: Boolean
        get() = false

    override val isDynamicColorsFeatureEnabled: Boolean
        get() = false

    override val isPaletteColorsFeatureEnabled: Boolean
        get() = false

    override val isNotificationsFeatureEnabled: Boolean
        get() = false

    override val isBiometricFeatureEnabled: Boolean
        get() = false

    override val isWidgetFeatureEnabled: Boolean
        get() = false

    override val isTileFeatureEnabled: Boolean
        get() = false

    override val isAppIconFeatureEnabled: Boolean
        get() = false

    override val isScreenshotFeatureEnabled: Boolean
        get() = false

    override val isGithubFeatureEnabled: Boolean
        get() = true

    override val isReviewAppFeatureEnabled: Boolean
        get() = false

    override val isUpdateAppFeatureEnabled: Boolean
        get() = false

    override val isAboutFeatureEnabled: Boolean
        get() = true

    override val settingsWindowInsets: WindowInsets
        @Composable get() = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)

    override val bottomBarModifier: Modifier
        get() = Modifier.navigationBarsPadding()

    @Composable
    override fun navigateToAppNotificationSettings(): () -> Unit {
        return {}
    }

    @Composable
    override fun rememberPostNotificationsPermissionHandler(
        areNotificationsEnabled: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ): () -> Unit {
        return {}
    }

    override val enabledIcon: IconAlias
        get() = IconAlias.Red

    override fun setIcon(iconAlias: IconAlias) {}

    override val grammaticalGender: SealedString
        get() = GrammaticalGender.NotSpecified()

    override fun setGrammaticalGender(grammaticalGender: Int) {}
}