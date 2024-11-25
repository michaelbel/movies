package org.michaelbel.movies.interactor.impl

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.interactor.SettingsUiInteractor

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
}