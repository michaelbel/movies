package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.ui.shortcuts.installShortcuts

/**
 * Per-App Language depends on AppCompatActivity (not ComponentActivity).
 */
@AndroidEntryPoint
internal class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installShortcuts()
        setContent {
            MainActivityContent()
        }
    }
}