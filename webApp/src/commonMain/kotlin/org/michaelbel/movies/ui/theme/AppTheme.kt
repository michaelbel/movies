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

@Composable
fun AppTheme(
    themeData: ThemeData = ThemeData.Default,
    content: @Composable () -> Unit
) {
    val seedColorPalettes = Color(themeData.seedColor).toTonalPalettes(paletteStyles.getOrElse(themeData.paletteKey) { PaletteStyle.TonalSpot })
    val light = if (themeData.paletteColors) seedColorPalettes.paletteLightColorScheme else lightColorScheme()
    val dark = if (themeData.paletteColors) seedColorPalettes.paletteDarkColorScheme else darkColorScheme()

    val colorScheme = when (themeData.appTheme) {
        AppTheme.NightNo -> light
        AppTheme.NightYes -> dark
        AppTheme.FollowSystem -> if (isSystemInDarkTheme()) dark else light
    }

    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        motionScheme = MotionScheme.expressive(),
        shapes = MoviesShapes,
        typography = MoviesTypography(),
        content = content
    )
}
