package org.michaelbel.movies.widget.theme

import androidx.compose.runtime.Composable
import androidx.glance.GlanceTheme

@Composable
internal fun MoviesGlanceTheme(
    content: @Composable () -> Unit
) {
    GlanceTheme(
        content = content
    )
}