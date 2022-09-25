package org.michaelbel.movies.theme

import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.michaelbel.movies.ui.ComposeTheme
import org.michaelbel.movies.ui.SystemTheme
import org.michaelbel.movies.ui.ktx.dynamicColorScheme

private val LightColorScheme = lightColorScheme()

private val DarkColorScheme = darkColorScheme()

@Composable
fun MoviesTheme(
    theme: SystemTheme = SystemTheme.FollowSystem,
    dynamicColors: Boolean = Build.VERSION.SDK_INT >= 31,
    content: @Composable () -> Unit
) {
    val context: Context = LocalContext.current
    val systemUiController: SystemUiController = rememberSystemUiController()

    val composeTheme: ComposeTheme = when (theme) {
        SystemTheme.NightNo -> {
            ComposeTheme(
                colorScheme = if (dynamicColors) {
                    dynamicLightColorScheme(context)
                } else {
                    LightColorScheme
                },
                statusBarDarkContentEnabled = true
            )
        }
        SystemTheme.NightYes -> {
            ComposeTheme(
                colorScheme = if (dynamicColors) {
                    dynamicDarkColorScheme(context)
                } else {
                    DarkColorScheme
                },
                statusBarDarkContentEnabled = false
            )
        }
        SystemTheme.FollowSystem -> {
            val darkTheme: Boolean = isSystemInDarkTheme()
            ComposeTheme(
                colorScheme = if (dynamicColors) {
                    context.dynamicColorScheme(darkTheme)
                } else {
                    if (darkTheme) {
                        DarkColorScheme
                    } else {
                        LightColorScheme
                    }
                },
                statusBarDarkContentEnabled = !darkTheme
            )
        }
    }

    val (colorScheme, statusBarDarkContentEnabled) = composeTheme

    systemUiController.statusBarDarkContentEnabled = statusBarDarkContentEnabled

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}