@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.michaelbel.movies.main.MainContent

fun main() {
    CanvasBasedWindow(
        title = "Movies",
        canvasElementId = "ComposeTarget"
    ) {
        MainContent()
    }
}