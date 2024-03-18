package org.michaelbel.movies.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

@Suppress("unused")
private val tmdbPrimary = Color(0xFF01D277)

@Suppress("unused")
private val tmdbSecondary = Color(0xFF081C24)

private val amoledSurface = Color(0xFF141414)

/** Light default theme color scheme. */
actual val LightColorScheme = lightColorScheme()

/** Dark default theme color scheme. */
actual val DarkColorScheme = darkColorScheme()

actual val AmoledColorScheme = darkColorScheme(
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