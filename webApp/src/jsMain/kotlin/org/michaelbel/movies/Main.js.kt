@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.compose.KoinApplication
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.main.MainContent

fun main() {
    onWasmReady {
        CanvasBasedWindow(
            title = "Movies",
            canvasElementId = "ComposeTarget"
        ) {
            KoinApplication(
                application = {
                    modules(appKoinModule)
                }
            ) {
                MainContent()
            }
        }
    }
}