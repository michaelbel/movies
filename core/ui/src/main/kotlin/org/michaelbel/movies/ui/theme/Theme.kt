package org.michaelbel.movies.ui.theme

import android.graphics.Color
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.ktx.context
import org.michaelbel.movies.ui.theme.model.ComposeTheme
import org.michaelbel.movies.ui.theme.provider.MoviesRippleTheme

@Composable
fun MoviesTheme(
    theme: AppTheme = AppTheme.FollowSystem,
    dynamicColors: Boolean = false,
    enableEdgeToEdge: (SystemBarStyle, SystemBarStyle) -> Unit = { _,_ -> },
    content: @Composable () -> Unit
) {
    val (colorScheme, detectDarkMode) = when (theme) {
        AppTheme.NightNo -> {
            ComposeTheme(
                colorScheme = if (dynamicColors) dynamicLightColorScheme(context) else LightColorScheme,
                detectDarkMode = false
            )
        }
        AppTheme.NightYes -> {
            ComposeTheme(
                colorScheme = if (dynamicColors) dynamicDarkColorScheme(context) else DarkColorScheme,
                detectDarkMode = true
            )
        }
        AppTheme.FollowSystem -> {
            val darkTheme: Boolean = isSystemInDarkTheme()
            ComposeTheme(
                colorScheme = if (dynamicColors) {
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                } else {
                    if (darkTheme) DarkColorScheme else LightColorScheme
                },
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
        SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { detectDarkMode },
        SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { detectDarkMode }
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MoviesShapes,
        typography = MoviesTypography
    ) {
        CompositionLocalProvider(
            value = LocalRippleTheme provides MoviesRippleTheme,
            content = content
        )
    }
}