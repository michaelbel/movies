package org.michaelbel.movies.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.michaelbel.movies.ui.color.PaletteStyle
import org.michaelbel.movies.ui.color.TonalPalettes
import org.michaelbel.movies.ui.color.hct.Hct

@Suppress("unused")
private val tmdbPrimary = Color(0xFF01D277)

@Suppress("unused")
private val tmdbSecondary = Color(0xFF081C24)

private val amoledSurface = Color(0xFF141414)

val paletteStyles = listOf(
    PaletteStyle.TonalSpot,
    PaletteStyle.Spritz,
    PaletteStyle.FruitSalad,
    PaletteStyle.Vibrant,
    PaletteStyle.Monochrome
)

val colorList = ((4..10) + (1..3)).map { it * 35.0 }.map { Color(Hct.from(it, 40.0, 40.0).toInt()) }

@Composable
fun Number.a1(tonalPalettes: TonalPalettes): Color {
    return tonalPalettes accent1 toDouble()
}

@Composable
fun Number.a2(tonalPalettes: TonalPalettes): Color {
    return tonalPalettes accent2 toDouble()
}

@Composable
fun Number.a3(tonalPalettes: TonalPalettes): Color {
    return tonalPalettes accent3 toDouble()
}

@Composable
fun Number.n1(tonalPalettes: TonalPalettes): Color {
    return tonalPalettes neutral1 toDouble()
}

@Composable
fun Number.n2(tonalPalettes: TonalPalettes): Color {
    return tonalPalettes neutral2 toDouble()
}

/** Light default theme color scheme. */
val LightColorScheme = lightColorScheme()

/** Dark default theme color scheme. */
val DarkColorScheme = darkColorScheme()

val TonalPalettes.paletteLightColorScheme: ColorScheme
    @Composable get() = lightColorScheme(
        background = 98.n1(this),
        inverseOnSurface = 95.n1(this),
        inversePrimary = 80.a1(this),
        inverseSurface = 20.n1(this),
        onBackground = 10.n1(this),
        onPrimary = 100.a1(this),
        onPrimaryContainer = 10.a1(this),
        onSecondary = 100.a2(this),
        onSecondaryContainer = 10.a2(this),
        onSurface = 10.n1(this),
        onSurfaceVariant = 30.n2(this),
        onTertiary = 100.a3(this),
        onTertiaryContainer = 10.a3(this),
        outline = 50.n2(this),
        outlineVariant = 80.n2(this),
        primary = 40.a1(this),
        primaryContainer = 90.a1(this),
        // scrim = 0.n1(this),
        secondary = 40.a2(this),
        secondaryContainer = 90.a2(this),
        surface = 98.n1(this),
        surfaceVariant = 90.n2(this),
        tertiary = 40.a3(this),
        tertiaryContainer = 90.a3(this),
        surfaceBright = 98.n1(this),
        surfaceDim = 87.n1(this),
        surfaceContainerLowest = 100.n1(this),
        surfaceContainerLow = 96.n1(this),
        surfaceContainer = 94.n1(this),
        surfaceContainerHigh = 92.n1(this),
        surfaceContainerHighest = 90.n1(this)
    )


val TonalPalettes.paletteDarkColorScheme: ColorScheme
    @Composable get() = darkColorScheme(
        background = 6.n1(this),
        inverseOnSurface = 20.n1(this),
        inversePrimary = 40.a1(this),
        inverseSurface = 90.n1(this),
        onBackground = 90.n1(this),
        onPrimary = 20.a1(this),
        onPrimaryContainer = 90.a1(this),
        onSecondary = 20.a2(this),
        onSecondaryContainer = 90.a2(this),
        onSurface = 90.n1(this),
        onSurfaceVariant = 80.n2(this),
        onTertiary = 20.a3(this),
        onTertiaryContainer = 90.a3(this),
        outline = 60.n2(this),
        outlineVariant = 30.n2(this),
        primary = 80.a1(this),
        primaryContainer = 30.a1(this),
        // scrim = 0.n1(this),
        secondary = 80.a2(this),
        secondaryContainer = 30.a2(this),
        surface = 6.n1(this),
        surfaceVariant = 30.n2(this),
        tertiary = 80.a3(this),
        tertiaryContainer = 30.a3(this),
        surfaceBright = 24.n1(this),
        surfaceDim = 6.n1(this),
        surfaceContainerLowest = 4.n1(this),
        surfaceContainerLow = 10.n1(this),
        surfaceContainer = 12.n1(this),
        surfaceContainerHigh = 17.n1(this),
        surfaceContainerHighest = 22.n1(this)
    )

val AmoledColorScheme = darkColorScheme(
    primary = Color.White,
    onPrimary = Color.White,
    primaryContainer = Color.Black,
    onPrimaryContainer = Color.White,
    inversePrimary = amoledSurface,
    secondary = Color.White,
    onSecondaryContainer = Color.White,
    surfaceTint = amoledSurface,
    onSurface = Color.White,
    surfaceVariant = amoledSurface,
    onSurfaceVariant = Color.White,
    error = Color.White,
    outline = Color.White
)