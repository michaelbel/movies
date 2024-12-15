package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable

expect val isDebug: Boolean

expect val isPortrait: Boolean

expect fun statusBarStyle(detectDarkMode: Boolean): Any

@Composable
expect fun navigationBarStyle(detectDarkMode: Boolean): Any

expect val displayCutoutWindowInsets: WindowInsets

expect val USE_PLATFORM_DEFAULT_WIDTH: Boolean