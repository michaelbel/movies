package org.michaelbel.moviemade.app.ui

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme()

private val DarkColorScheme = darkColorScheme()

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val context: Context = LocalContext.current

    val dynamicColorScheme: ColorScheme = if (darkTheme) {
        dynamicDarkColorScheme(context)
    } else {
        dynamicLightColorScheme(context)
    }

    val autoColorScheme: ColorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val appColorScheme: ColorScheme = if (Build.VERSION.SDK_INT >= 31) {
        dynamicColorScheme
    } else {
        autoColorScheme
    }

    MaterialTheme(
        colorScheme = appColorScheme,
        content = content
    )
}