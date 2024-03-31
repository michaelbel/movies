package org.michaelbel.movies.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.theme.model.ComposeTheme
import org.michaelbel.movies.ui.theme.provider.MoviesRippleTheme

@Composable
actual fun MoviesTheme(
    themeData: ThemeData,
    theme: AppTheme,
    enableEdgeToEdge: (Any, Any) -> Unit,
    content: @Composable () -> Unit
) {
    val (colorScheme, detectDarkMode) = when (themeData.appTheme) {
        AppTheme.NightNo -> {
            ComposeTheme(
                colorScheme = LightColorScheme,
                detectDarkMode = false
            )
        }
        AppTheme.NightYes -> {
            ComposeTheme(
                colorScheme = DarkColorScheme,
                detectDarkMode = true
            )
        }
        AppTheme.FollowSystem -> {
            val darkTheme = isSystemInDarkTheme()
            ComposeTheme(
                colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
                detectDarkMode = darkTheme
            )
        }
        AppTheme.Amoled -> {
            ComposeTheme(
                colorScheme = AmoledColorScheme,
                detectDarkMode = true
            )
        }
    }

    enableEdgeToEdge(
        Any(),
        Any()
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MoviesShapes
    ) {
        CompositionLocalProvider(LocalRippleTheme provides MoviesRippleTheme) {
            content()
        }
    }
}