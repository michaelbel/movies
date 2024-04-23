package org.michaelbel.movies.common.ktx

/*import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

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
}*/