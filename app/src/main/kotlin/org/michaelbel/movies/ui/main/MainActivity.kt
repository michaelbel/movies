package org.michaelbel.movies.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.michaelbel.movies.app.playcore.InAppUpdate
import org.michaelbel.movies.ui.AppTheme

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject lateinit var inAppUpdate: InAppUpdate

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val currentTheme = viewModel.currentTheme.collectAsState(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM).value

            ProvideWindowInsets {
                AppTheme(theme = currentTheme) {
                    MainScreen(::onAppUpdateClick)
                }
            }
        }
    }

    private fun onAppUpdateClick() {
        inAppUpdate.startUpdateFlow(this)
    }
}