package org.michaelbel.movies.ui.theme

import androidx.compose.runtime.Composable
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme

@Composable
expect fun MoviesTheme(
    themeData: ThemeData = ThemeData.Default,
    theme: AppTheme = themeData.appTheme,
    enableEdgeToEdge: (Any, Any) -> Unit = { _,_ -> },
    content: @Composable () -> Unit
)