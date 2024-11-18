@file:OptIn(ExperimentalComposeUiApi::class)

package org.michaelbel.movies

import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.main.MainContent
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.theme.MoviesTheme

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
                val themeData by viewModel.themeData.collectAsStateCommon()

                MoviesTheme(
                    themeData = ThemeData(
                        appTheme = themeData.appTheme,
                        dynamicColors = false,
                        paletteKey = themeData.paletteKey,
                        seedColor = themeData.seedColor
                    ),
                    theme = themeData.appTheme,
                    enableEdgeToEdge = { _,_ -> }
                ) {
                    MainContent()
                }
            }
        }
    }
}