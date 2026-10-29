@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.michaelbel.movies.ui.theme.WebFonts

fun main() {
    ComposeViewport(
        viewportContainerId = "ComposeTarget"
    ) {
        WebFonts {
            WebApp()
        }
    }
}
