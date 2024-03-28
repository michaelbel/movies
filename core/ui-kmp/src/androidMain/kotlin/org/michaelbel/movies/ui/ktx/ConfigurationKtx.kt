package org.michaelbel.movies.ui.ktx

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

actual val isPortrait: Boolean
    @Composable get() {
        val configuration = LocalConfiguration.current
        return configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }