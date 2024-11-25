package org.michaelbel.movies.interactor.impl

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import org.michaelbel.movies.common.ktx.appNotificationSettingsIntent
import org.michaelbel.movies.interactor.SettingsUiInteractor

class SettingsUiInteractorImpl: SettingsUiInteractor {

    override val isNavigationIconVisible: Boolean
        get() = false

    override val isLanguageFeatureEnabled: Boolean
        get() = true

    override val isThemeFeatureEnabled: Boolean
        get() = true

    override val isFeedViewFeatureEnabled: Boolean
        get() = true

    override val isMovieListFeatureEnabled: Boolean
        get() = true

    override val isGenderFeatureEnabled: Boolean
        @ChecksSdkIntAtLeast(34) get() = Build.VERSION.SDK_INT >= 34

    override val isNotificationsFeatureEnabled: Boolean
        @ChecksSdkIntAtLeast(33) get() = Build.VERSION.SDK_INT >= 33

    override val isBiometricFeatureEnabled: Boolean
        get() = true

    override val isWidgetFeatureEnabled: Boolean
        @ChecksSdkIntAtLeast(26) get() = Build.VERSION.SDK_INT >= 26

    override val isTileFeatureEnabled: Boolean
        @ChecksSdkIntAtLeast(33) get() = Build.VERSION.SDK_INT >= 33

    override val isAppIconFeatureEnabled: Boolean
        get() = true

    override val isScreenshotFeatureEnabled: Boolean
        get() = true

    override val isGithubFeatureEnabled: Boolean
        get() = true

    override val isReviewAppFeatureEnabled: Boolean
        get() = true

    override val isUpdateAppFeatureEnabled: Boolean
        get() = true

    override val isAboutFeatureEnabled: Boolean
        get() = true

    override val bottomBarModifier: Modifier
        get() = Modifier

    @Composable
    override fun navigateToAppNotificationSettings(): () -> Unit {
        val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        val context = LocalContext.current
        return { resultContract.launch(context.appNotificationSettingsIntent) }
    }

    @Composable
    override fun rememberPostNotificationsPermissionHandler(
        areNotificationsEnabled: Boolean,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ): () -> Unit {
        val context = LocalContext.current
        val postNotificationsPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            when {
                granted -> onPermissionGranted()
                else -> {
                    if (Build.VERSION.SDK_INT >= 33) {
                        val shouldRequest = (context as Activity).shouldShowRequestPermissionRationale(
                            Manifest.permission.POST_NOTIFICATIONS)
                        if (!shouldRequest) {
                            onPermissionDenied()
                        }
                    }
                }
            }
        }
        val resultContract = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}
        return {
            if (areNotificationsEnabled) {
                val intent = context.appNotificationSettingsIntent
                resultContract.launch(intent)
            } else if (Build.VERSION.SDK_INT >= 33) {
                postNotificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}