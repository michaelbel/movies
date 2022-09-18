package org.michaelbel.movies.theme

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme()

private val DarkColorScheme = darkColorScheme()

@Composable
fun AppTheme(
    theme: Int = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColors: Boolean = Build.VERSION.SDK_INT >= 31,
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

    val colorScheme: ColorScheme = when (theme) {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> {
            if (dynamicColors) {
                dynamicColorScheme
            } else {
                autoColorScheme
            }
        }
        AppCompatDelegate.MODE_NIGHT_NO -> {
            if (dynamicColors) dynamicLightColorScheme(context) else LightColorScheme
        }
        AppCompatDelegate.MODE_NIGHT_YES -> {
            if (dynamicColors) dynamicDarkColorScheme(context) else DarkColorScheme
        }
        else -> throw Exception()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}