package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.di.appKoinModule
import org.michaelbel.movies.main.MainContent
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun IosMainContent() {
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