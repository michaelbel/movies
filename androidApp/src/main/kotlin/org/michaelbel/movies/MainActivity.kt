package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.michaelbel.movies.common.ktx.launchAndCollectIn
import org.michaelbel.movies.ui.ktx.resolveNotificationPreferencesIntent
import org.michaelbel.movies.ui.ktx.setScreenshotBlockEnabled
import org.michaelbel.movies.ui.ktx.supportRegisterScreenCaptureCallback
import org.michaelbel.movies.ui.ktx.supportUnregisterScreenCaptureCallback
import org.michaelbel.movies.ui.shortcuts.installShortcuts

internal class MainActivity: FragmentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashLoading.value } }
        super.onCreate(savedInstanceState)
        installShortcuts()
        setContent {
            MainActivityContent { statusBarStyle, navigationBarStyle ->
                enableEdgeToEdge(statusBarStyle as SystemBarStyle, navigationBarStyle as SystemBarStyle)
            }
        }
        resolveNotificationPreferencesIntent()
        viewModel.run {
            isScreenshotBlockEnabled.launchAndCollectIn(this@MainActivity) { enabled ->
                window.setScreenshotBlockEnabled(enabled)
            }
            authenticateFlow.launchAndCollectIn(this@MainActivity) { authenticate(this@MainActivity) }
            cancelFlow.launchAndCollectIn(this@MainActivity) { finish() }
        }
    }

    override fun onStart() {
        super.onStart()
        supportRegisterScreenCaptureCallback()
    }

    override fun onStop() {
        super.onStop()
        supportUnregisterScreenCaptureCallback()
    }
}