package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.koinConfiguration
import org.michaelbel.movies.about.AboutDialog
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.common.browser.DesktopTmdbAuthCallbackServer
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.main.MainScreen
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.main.intent.MainIntent
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.collectAsStateCommon
import org.michaelbel.movies.ui.theme.AppTheme
import java.awt.Desktop
import java.awt.Dimension
import java.awt.EventQueue

fun main() {
    System.setProperty("apple.awt.application.name", "Movies")
    if (System.getProperty("movies.version") == null) {
        System.setProperty("movies.version", "3.1.0")
    }
    if (System.getProperty("movies.build") == null) {
        System.setProperty("movies.build", "1")
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(
                position = WindowPosition.Aligned(Alignment.Center),
                size = DpSize(800.dp, 600.dp)
            ),
            title = "Movies",
            alwaysOnTop = false,
            onKeyEvent = { false }
        ) {
            window.minimumSize = Dimension(800, 600)

            val icon = painterResource(MoviesIcons.LauncherRed)
            val density = LocalDensity.current
            val layoutDirection = LocalLayoutDirection.current
            LaunchedEffect(icon) {
                window.iconImage = icon.toAwtImage(
                    density = density,
                    layoutDirection = layoutDirection
                )
            }

            App()
        }
    }
}

@Composable
private fun App() {
    KoinApplication(
        configuration = koinConfiguration {
            modules(appKoinModule)
        }
    ) {
        val viewModel = koinInject<MainViewModel>()
        val aboutInteractor = koinInject<AboutInteractor>()
        val state by viewModel.stateFlow.collectAsStateCommon()
        var isAboutDialogVisible by remember { mutableStateOf(false) }

        LaunchedEffect(viewModel) {
            DesktopTmdbAuthCallbackServer.callbackFlow.collect { callback ->
                viewModel.dispatch(
                    MainIntent.NavigateToMain(
                        requestToken = callback.requestToken,
                        approved = callback.approved
                    )
                )
            }
        }

        DisposableEffect(Unit) {
            if (Desktop.isDesktopSupported()) {
                val desktop = Desktop.getDesktop()
                if (desktop.isSupported(Desktop.Action.APP_ABOUT)) {
                    desktop.setAboutHandler {
                        EventQueue.invokeLater {
                            isAboutDialogVisible = true
                        }
                    }
                }
            }
            onDispose {}
        }

        withViewModelStoreOwner {
            AppTheme(
                themeData = ThemeData(
                    appTheme = state.themeData.appTheme,
                    dynamicColors = false,
                    paletteColors = state.themeData.paletteColors,
                    paletteKey = state.themeData.paletteKey,
                    seedColor = state.themeData.seedColor
                ),
                theme = state.themeData.appTheme,
                enableEdgeToEdge = { _,_ -> }
            ) {
                MainScreen()

                if (isAboutDialogVisible) {
                    AboutDialog(
                        themeData = ThemeData(
                            appTheme = state.themeData.appTheme,
                            dynamicColors = false,
                            paletteColors = state.themeData.paletteColors,
                            paletteKey = state.themeData.paletteKey,
                            seedColor = state.themeData.seedColor
                        ),
                        theme = state.themeData.appTheme,
                        versionName = aboutInteractor.versionName,
                        versionCode = aboutInteractor.versionCode,
                        onDismissRequest = { isAboutDialogVisible = false }
                    )
                }
            }
        }
    }
}

private class ComposeViewModelStoreOwner: ViewModelStoreOwner {
    override val viewModelStore = ViewModelStore()
    fun dispose() { viewModelStore.clear() }
}

@Composable
private fun rememberComposeViewModelStoreOwner(): ViewModelStoreOwner {
    val viewModelStoreOwner = remember { ComposeViewModelStoreOwner() }
    DisposableEffect(viewModelStoreOwner) {
        onDispose { viewModelStoreOwner.dispose() }
    }
    return viewModelStoreOwner
}

@Composable
fun withViewModelStoreOwner(content: @Composable () -> Unit) {
    if (LocalViewModelStoreOwner.current != null) {
        content()
    } else {
        CompositionLocalProvider(
            LocalViewModelStoreOwner provides rememberComposeViewModelStoreOwner(),
            content = content
        )
    }
}
