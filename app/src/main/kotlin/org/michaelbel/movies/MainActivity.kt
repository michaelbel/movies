package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.common.shortcuts.installShortcuts
import org.michaelbel.movies.ui.theme.SystemTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installShortcuts()
        setContent {
            val currentTheme: SystemTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
            val dynamicColors: Boolean by viewModel.dynamicColors.collectAsStateWithLifecycle()

            val navHostController: NavHostController = rememberNavController().apply {
                addOnDestinationChangedListener { _, destination, arguments ->
                    viewModel.analyticsTrackDestination(destination, arguments)
                }
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