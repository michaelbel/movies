package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.navigation.ktx.addOnDestinationChangedListener
import org.michaelbel.movies.ui.shortcuts.installShortcuts
import org.michaelbel.movies.ui.theme.MoviesTheme

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
            val currentTheme: AppTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
            val dynamicColors: Boolean by viewModel.dynamicColors.collectAsStateWithLifecycle()

            val navHostController: NavHostController = rememberNavController().apply {
                addOnDestinationChangedListener(viewModel::analyticsTrackDestination)
            }

            MoviesTheme(
                theme = currentTheme,
                dynamicColors = dynamicColors
            ) {
                MainActivityContent(
                    navHostController = navHostController
                )
            }
        }
    }
}