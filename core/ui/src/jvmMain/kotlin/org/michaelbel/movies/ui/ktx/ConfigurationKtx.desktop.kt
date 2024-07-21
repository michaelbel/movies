package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable

actual val isPortrait: Boolean
    get() = false

actual fun statusBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

@Composable
actual fun navigationBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}