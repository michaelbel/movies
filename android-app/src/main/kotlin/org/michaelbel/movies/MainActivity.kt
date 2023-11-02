package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installShortcuts()
        setContent {
            MainActivityContent(
                onStartUpdateFlow = { viewModel.startUpdateFlow(this) }
            )
        }
    }
}