package org.michaelbel.movies.ui.theme.ktx

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

fun Context.dynamicColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) dynamicDarkColorScheme(this) else dynamicLightColorScheme(this)
}