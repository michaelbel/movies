package org.michaelbel.movies.ui.ktx

import android.content.Context
import androidx.annotation.RequiresApi
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

@RequiresApi(31)
fun Context.dynamicColorScheme(darkTheme: Boolean): ColorScheme {
    return if (darkTheme) {
        dynamicDarkColorScheme(this)
    } else {
        dynamicLightColorScheme(this)
    }
}