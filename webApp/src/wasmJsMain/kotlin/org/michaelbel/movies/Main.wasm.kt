@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow

fun main() {
    CanvasBasedWindow(
        title = "Movies",
        canvasElementId = "ComposeTarget"
    ) {
        MainContent()
    }
}