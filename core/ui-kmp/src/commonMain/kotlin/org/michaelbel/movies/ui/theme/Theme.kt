package org.michaelbel.movies.ui.theme

import androidx.compose.runtime.Composable
import org.michaelbel.movies.common.theme.AppTheme

@Composable
expect fun MoviesTheme(
    theme: AppTheme = AppTheme.FollowSystem,
    dynamicColors: Boolean = false,
    enableEdgeToEdge: (Any, Any) -> Unit = { _,_ -> },
    content: @Composable () -> Unit
)