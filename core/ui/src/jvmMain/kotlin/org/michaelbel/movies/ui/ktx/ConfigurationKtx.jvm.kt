package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable

actual val isDebug: Boolean
    get() = true

actual val isPortrait: Boolean
    get() = false

actual fun statusBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

@Composable
actual fun navigationBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

actual val displayCutoutWindowInsets: WindowInsets
    get() = WindowInsets(0, 0, 0, 0)

actual const val USE_PLATFORM_DEFAULT_WIDTH: Boolean = true