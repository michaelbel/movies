package org.michaelbel.movies.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.ui.entity.MovieCardStyle
import org.michaelbel.movies.ui.strings.MoviesStrings
import platform.Foundation.NSURL
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import kotlin.coroutines.CoroutineContext

actual val isDebug: Boolean
    get() = true

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
    get() = false

actual val bottomSheetUsePlatformDefaultWidth: Boolean
    get() = true

@Composable
actual fun pageStaggeredGridCells(style: MovieCardStyle): StaggeredGridCells {
    return StaggeredGridCells.Fixed(2)
}

@Suppress("ModifierFactoryExtensionFunction")
actual val modifierDisplayCutoutWindowInsets: Modifier
    get() = Modifier.windowInsetsPadding(displayCutoutWindowInsets)

actual val SettingsGenderText: String
    @Composable get() = stringResource(MoviesStrings.settings_gender)

actual val SettingsListItemCount: Int = 6

@Composable
actual fun rememberSpeechRecognitionLauncher(onInputText: (String) -> Unit): () -> Unit = {}

@Composable
actual fun shareText(text: String, title: String): () -> Unit = {
    val currentViewController = UIApplication.sharedApplication().keyWindow?.rootViewController
    val activityViewController = UIActivityViewController(listOf(text), null)
    currentViewController?.presentViewController(
        viewControllerToPresent = activityViewController,
        animated = true,
        completion = null
    )
}

@Composable
actual fun navigateToImageUri(): (uri: String) -> Unit = { uri ->
    val nsUrl = NSURL.URLWithString(uri)
    if (nsUrl != null) {
        UIApplication.sharedApplication().openURL(
            nsUrl,
            options = emptyMap<Any?, Any?>(),
            completionHandler = null
        )
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
    LaunchedEffect(flow, key1, key2) {
        flow.collect(onEvent)
    }
}

@Composable
actual fun <T> StateFlow<T>.collectAsStateCommon(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State,
    context: CoroutineContext
): State<T> = collectAsState()
