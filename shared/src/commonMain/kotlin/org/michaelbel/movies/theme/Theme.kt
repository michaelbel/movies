package org.michaelbel.movies.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

object MoviesColors {
    val background = Color(0xFFFFFFFF)
    val onBackground = Color(0xFF19191C)
}

@Composable
fun MoviesTheme(content: @Composable () -> Unit) {
    isSystemInDarkTheme()

    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            background = MoviesColors.background,
            onBackground = MoviesColors.onBackground
        )
    ) {
        ProvideTextStyle(LocalTextStyle.current.copy(letterSpacing = 0.sp)) {
            content()
        }
    }
}