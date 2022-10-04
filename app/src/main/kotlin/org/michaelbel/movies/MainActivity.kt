package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.ui.MoviesTheme
import org.michaelbel.movies.ui.SystemTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val currentTheme: SystemTheme = viewModel.currentTheme
                .collectAsState(SystemTheme.FollowSystem).value
            val dynamicColors: Boolean = viewModel.dynamicColors.collectAsState(false).value

            MoviesTheme(
                theme = currentTheme,
                dynamicColors = dynamicColors
            ) {
                MainActivityContent()
            }
        }
    }
}