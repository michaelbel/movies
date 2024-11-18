@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.main.MainContent
import org.michaelbel.movies.main.MainViewModel

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
                val viewModel = koinInject<MainViewModel>()
                val themeData by viewModel.themeData.collectAsState()

                MainContent()
            }
        }
    }
}