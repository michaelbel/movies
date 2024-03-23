package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.common.ktx.launchAndCollectIn
import org.michaelbel.movies.ui.ktx.resolveNotificationPreferencesIntent
import org.michaelbel.movies.ui.shortcuts.installShortcuts

@AndroidEntryPoint
internal class MainActivity: FragmentActivity() {

    private val viewModel: MainViewModel by viewModels()

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
            authenticateFlow.launchAndCollectIn(this@MainActivity) { authenticate(this@MainActivity) }
            cancelFlow.launchAndCollectIn(this@MainActivity) { finish() }
        }
    }
}