package org.michaelbel.movies

import androidx.compose.ui.window.singleWindowApplication
import moe.tlaster.precompose.PreComposeApp
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

fun main() = singleWindowApplication(
    title = "Movies",
    icon = null
) {
    PreComposeApp {
        MoviesTheme(
            themeData = ThemeData(
                appTheme = AppTheme.FollowSystem,
                dynamicColors = false,
                paletteKey = 0,
                seedColor = 0
            ),
            theme = AppTheme.FollowSystem,
            enableEdgeToEdge = { _,_ -> }
        ) {
            MainWindowContent()
        }
    }
}