@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.color.PaletteStyle
import org.michaelbel.movies.ui.color.TonalPalettes.Companion.toTonalPalettes
import org.michaelbel.movies.ui.theme.model.ComposeTheme

@Composable
actual fun AppTheme(
    themeData: ThemeData,
    theme: AppTheme,
    enableEdgeToEdge: (Any, Any) -> Unit,
    content: @Composable () -> Unit
) {
    val seedColorPalettes = Color(themeData.seedColor).toTonalPalettes(paletteStyles.getOrElse(themeData.paletteKey) { PaletteStyle.TonalSpot })
    val paletteLightColorScheme = if (themeData.paletteColors) seedColorPalettes.paletteLightColorScheme else lightColorScheme()
    val paletteDarkColorScheme = if (themeData.paletteColors) seedColorPalettes.paletteDarkColorScheme else darkColorScheme()
    val (colorScheme, _) = when (themeData.appTheme) {
        AppTheme.NightNo -> {
            ComposeTheme(
                colorScheme = paletteLightColorScheme,
                detectDarkMode = false
            )
        }
        AppTheme.NightYes -> {
            ComposeTheme(
                colorScheme = paletteDarkColorScheme,
                detectDarkMode = true
            )
        }
        AppTheme.FollowSystem -> {
            val darkTheme = isSystemInDarkTheme()
            ComposeTheme(
                colorScheme = if (darkTheme) paletteDarkColorScheme else paletteLightColorScheme,
                detectDarkMode = darkTheme
            )
        }
    }

    enableEdgeToEdge(
        Any(),
        Any()
    )

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        motionScheme = MotionScheme.expressive(),
        shapes = MoviesShapes
    ) {
        content()
    }
}
