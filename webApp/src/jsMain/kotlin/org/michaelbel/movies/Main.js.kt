package org.michaelbel.movies

import androidx.compose.ui.window.ComposeViewport
import org.michaelbel.movies.webui.theme.WebFonts

fun main() {
    ComposeViewport(
        viewportContainerId = "ComposeTarget"
    ) {
        WebFonts {
            WebApp()
        }
    }
}
