package org.michaelbel.movies.main

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
@Preview
fun IosMainContent() {
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
        MainContent()
    }
}