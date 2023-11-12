package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.ui.shortcuts.installShortcuts

/**
 * Per-App Language depends on AppCompatActivity (not ComponentActivity).
 */
@AndroidEntryPoint
internal class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        /** Configure edge-to-edge display. */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installShortcuts()
        setContent {
            MainActivityContent(
                onStartUpdateFlow = { viewModel.startUpdateFlow(this) }
            )
        }
    }
}