package org.michaelbel.movies.ui.ktx

import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

actual val modifierDisplayCutoutWindowInsets: Modifier
    @Composable get() = Modifier.windowInsetsPadding(displayCutoutWindowInsets)