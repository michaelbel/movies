@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.theme

import androidx.activity.SystemBarStyle
import androidx.compose.runtime.Composable
import org.michaelbel.movies.common.theme.AppTheme

@Composable
expect fun MoviesTheme(
    theme: AppTheme = AppTheme.FollowSystem,
    dynamicColors: Boolean = false,
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit = { _,_ -> },
    content: @Composable () -> Unit
)