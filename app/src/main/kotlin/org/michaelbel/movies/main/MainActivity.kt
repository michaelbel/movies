package org.michaelbel.movies.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.michaelbel.movies.app.playcore.InAppUpdate
import org.michaelbel.movies.theme.AppTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject lateinit var inAppUpdate: InAppUpdate

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            val currentTheme = viewModel.currentTheme.collectAsState(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            ).value

            AppTheme(
                theme = currentTheme,
                dynamicColors = false
            ) {
                MainActivityContent(
                    onAppUpdateClicked = ::onAppUpdateClick
                )
            }
        }
    }

    private fun onAppUpdateClick() {
        inAppUpdate.startUpdateFlow(this)
    }
}