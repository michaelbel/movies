package org.michaelbel.movies

import android.app.Activity.ScreenCaptureCallback
import android.os.Build
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.michaelbel.movies.common.ktx.launchAndCollectIn
import org.michaelbel.movies.main.MainContent
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.ui.ktx.resolveNotificationPreferencesIntent
import org.michaelbel.movies.ui.ktx.setScreenshotBlockEnabled
import org.michaelbel.movies.ui.ktx.supportRegisterScreenCaptureCallback
import org.michaelbel.movies.ui.ktx.supportUnregisterScreenCaptureCallback
import org.michaelbel.movies.ui.shortcuts.installShortcuts

internal class MainActivity: FragmentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val screenCaptureCallback: Any
        get() {
            return if (Build.VERSION.SDK_INT >= 34) {
                ScreenCaptureCallback {}
            } else {
                Unit
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply { setKeepOnScreenCondition { viewModel.splashLoading.value } }
        super.onCreate(savedInstanceState)
        installShortcuts()
        setContent {
            MainContent(
                enableEdgeToEdge = { statusBarStyle, navigationBarStyle ->
                    enableEdgeToEdge(statusBarStyle as SystemBarStyle, navigationBarStyle as SystemBarStyle)
                },
                onRequestReview = { viewModel.requestReview(this) },
                onRequestUpdate = { viewModel.requestUpdate(this) }
            )
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
        supportRegisterScreenCaptureCallback(screenCaptureCallback)
    }

    override fun onStop() {
        super.onStop()
        supportUnregisterScreenCaptureCallback(screenCaptureCallback)
    }
}