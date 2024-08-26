@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        CanvasBasedWindow(
            title = "Movies",
            canvasElementId = "ComposeTarget"
        ) {
            MainContent()
        }
    }
}