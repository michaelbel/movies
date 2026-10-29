package org.michaelbel.movies.webui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import movies.webapp.generated.resources.Res
import movies.webapp.generated.resources.roboto_bold
import movies.webapp.generated.resources.roboto_medium
import movies.webapp.generated.resources.roboto_regular
import org.jetbrains.compose.resources.preloadFont

@Composable
fun WebFonts(
    content: @Composable () -> Unit
) {
    val regular by preloadFont(
        resource = Res.font.roboto_regular,
        weight = FontWeight.Normal
    )
    val medium by preloadFont(
        resource = Res.font.roboto_medium,
        weight = FontWeight.Medium
    )
    val bold by preloadFont(
        resource = Res.font.roboto_bold,
        weight = FontWeight.Bold
    )

    if (regular != null && medium != null && bold != null) {
        content()
    }
}
