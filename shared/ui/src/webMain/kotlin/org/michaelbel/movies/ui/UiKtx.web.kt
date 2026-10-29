@file:OptIn(ExperimentalWasmJsInterop::class)

package org.michaelbel.movies.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.browser.window
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.entity.MovieCardStyle
import org.michaelbel.movies.ui.strings.MoviesStrings
import kotlin.coroutines.CoroutineContext
import kotlin.js.ExperimentalWasmJsInterop

actual val isDebug: Boolean
    get() = false

actual fun statusBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

@Composable
actual fun navigationBarStyle(detectDarkMode: Boolean): Any {
    return Any()
}

actual val displayCutoutWindowInsets: WindowInsets
    get() = WindowInsets(0, 0, 0, 0)

actual val dialogUsePlatformDefaultWidth: Boolean
    get() = true

actual val bottomSheetUsePlatformDefaultWidth: Boolean
    get() = true

@Composable
actual fun pageStaggeredGridCells(style: MovieCardStyle): StaggeredGridCells {
    return StaggeredGridCells.Adaptive(
        minSize = when (style) {
            MovieCardStyle.Row -> 280.dp
            MovieCardStyle.Column -> 220.dp
        }
    )
}

@Suppress("ModifierFactoryExtensionFunction")
actual val modifierDisplayCutoutWindowInsets: Modifier
    get() = Modifier

actual val SettingsGenderText: String
    @Composable get() = stringResource(MoviesStrings.settings_gender)

actual val SettingsListItemCount: Int = 6

@Composable
actual fun rememberSpeechRecognitionLauncher(onInputText: (String) -> Unit): () -> Unit = {}

@Composable
actual fun shareText(text: String, title: String): () -> Unit {
    return {
        window.navigator.clipboard.writeText(text)
    }
}

@Composable
actual fun navigateToImageUri(): (uri: String) -> Unit {
    return { uri ->
        window.open(uri, "_blank")
    }
}

@Composable
actual fun requestTileService(onSuccess: (String) -> Unit): () -> Unit {
    return {}
}

@Composable
actual fun rememberConnectivityClickHandler(): () -> Unit = {}

@Composable
actual fun <T> ObserveAsEvents(
    flow: Flow<T>,
    key1: Any?,
    key2: Any?,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner.lifecycle, key1, key2, flow) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}

@Composable
actual fun <T> StateFlow<T>.collectAsStateCommon(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State,
    context: CoroutineContext
): State<T> = collectAsState()
