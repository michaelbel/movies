package org.michaelbel.movies.ui.ktx

import android.content.res.Configuration
import androidx.activity.SystemBarStyle
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import org.michaelbel.movies.common.BuildConfig

actual val isDebug: Boolean
    get() = BuildConfig.DEBUG

actual val isPortrait: Boolean
    @Composable get() {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

actual fun statusBarStyle(detectDarkMode: Boolean): Any {
    return SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()) { detectDarkMode }
}

@Composable
actual fun navigationBarStyle(detectDarkMode: Boolean): Any {
    val configuration = LocalConfiguration.current
    val currentNightMode = configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return when (currentNightMode) {
        Configuration.UI_MODE_NIGHT_NO -> SystemBarStyle.light(Color.Transparent.toArgb(), Color.Transparent.toArgb())
        Configuration.UI_MODE_NIGHT_YES -> SystemBarStyle.dark(Color.Transparent.toArgb())
        else -> SystemBarStyle.auto(Color.Transparent.toArgb(), Color.Transparent.toArgb()) { detectDarkMode }
    }
}

actual val displayCutoutWindowInsets: WindowInsets
    @Composable get() = if (isPortrait) WindowInsets(0, 0, 0, 0) else WindowInsets.displayCutout