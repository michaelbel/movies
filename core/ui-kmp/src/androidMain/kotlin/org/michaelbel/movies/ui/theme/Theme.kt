package org.michaelbel.movies.ui.theme

import androidx.activity.SystemBarStyle
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.color.PaletteStyle
import org.michaelbel.movies.ui.color.TonalPalettes.Companion.toTonalPalettes
import org.michaelbel.movies.ui.ktx.context
import org.michaelbel.movies.ui.theme.model.ComposeTheme
import org.michaelbel.movies.ui.theme.provider.MoviesRippleTheme

private const val ColorTransparent = android.graphics.Color.TRANSPARENT

@Composable
actual fun MoviesTheme(
    themeData: ThemeData,
    theme: AppTheme,
    enableEdgeToEdge: (Any, Any) -> Unit,
    content: @Composable () -> Unit
) {
    val seedColorPalettes = Color(themeData.seedColor).toTonalPalettes(paletteStyles.getOrElse(themeData.paletteKey) { PaletteStyle.TonalSpot })
    val (colorScheme, detectDarkMode) = when (themeData.appTheme) {
        AppTheme.NightNo -> {
            ComposeTheme(
                colorScheme = if (themeData.dynamicColors) dynamicLightColorScheme(context) else seedColorPalettes.paletteLightColorScheme,
                detectDarkMode = false
            )
        }
        AppTheme.NightYes -> {
            ComposeTheme(
                colorScheme = if (themeData.dynamicColors) dynamicDarkColorScheme(context) else seedColorPalettes.paletteDarkColorScheme,
                detectDarkMode = true
            )
        }
        AppTheme.FollowSystem -> {
            val darkTheme = isSystemInDarkTheme()
            ComposeTheme(
                colorScheme = if (themeData.dynamicColors) {
                    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
                } else {
                    if (darkTheme) seedColorPalettes.paletteDarkColorScheme else seedColorPalettes.paletteLightColorScheme
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
        SystemBarStyle.auto(ColorTransparent, ColorTransparent) { detectDarkMode },
        SystemBarStyle.auto(ColorTransparent, ColorTransparent) { detectDarkMode }
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = MoviesShapes,
        typography = MoviesTypography
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides MoviesRippleTheme
        ) {
            content()
        }
    }
}