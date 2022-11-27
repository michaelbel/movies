package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.common.shortcuts.installShortcuts
import org.michaelbel.movies.ui.theme.MoviesTheme
import org.michaelbel.movies.ui.theme.model.AppTheme

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
            val layoutDirection: LayoutDirection by viewModel.layoutDirection.collectAsStateWithLifecycle()

            val navHostController: NavHostController = rememberNavController().apply {
                addOnDestinationChangedListener { _, destination, arguments ->
                    viewModel.analyticsTrackDestination(destination, arguments)
                }
            }

            MoviesTheme(
                theme = currentTheme,
                dynamicColors = dynamicColors
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    MainActivityContent(
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}