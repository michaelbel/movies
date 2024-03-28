package org.michaelbel.movies

import androidx.compose.ui.window.singleWindowApplication
import moe.tlaster.precompose.PreComposeApp
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

fun main() = singleWindowApplication(
    title = "Movies",
    icon = null
) {
    PreComposeApp {
        MoviesTheme(
            theme = AppTheme.FollowSystem,
            dynamicColors = false,
            enableEdgeToEdge = { _,_ -> }
        ) {
            MainWindowContent()
        }
    }
}