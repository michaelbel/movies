package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.main.MainContent
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.theme.MoviesTheme
import java.awt.Dimension

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(600.dp, 500.dp)
        ),
        title = "Movies",
        icon = painterResource(MoviesIcons.LauncherRed),
        alwaysOnTop = true,
        onKeyEvent = { false }
    ) {
        window.minimumSize = Dimension(600, 500)

        App()
    }
}

@Composable
private fun App() {
    KoinApplication(
        application = {
            modules(appKoinModule)
        }
    ) {
        val viewModel = koinInject<MainViewModel>()
        val themeData by viewModel.themeData.collectAsStateCommon()

        withViewModelStoreOwner {
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
internal fun withViewModelStoreOwner(content: @Composable () -> Unit) {
    if (LocalViewModelStoreOwner.current != null) {
        // Normal case: use system-provided owner
        content()
    } else {
        // Fallback case: use ViewModelStoreOwner with scope of this composable.
        // It's required for Compose Multiplatform for now because it's not providing default value yet.
        // Expected to be fixed in Compose Multiplatform 1.7.0
        CompositionLocalProvider(
            LocalViewModelStoreOwner provides rememberComposeViewModelStoreOwner(),
            content = content
        )
    }
}