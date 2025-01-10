package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable

expect val isPortrait: Boolean

expect fun statusBarStyle(detectDarkMode: Boolean): Any

@Composable
expect fun navigationBarStyle(detectDarkMode: Boolean): Any