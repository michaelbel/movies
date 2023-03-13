package org.michaelbel.movies.ui.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
private fun OnLifecycleEvent(
    onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit
) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle: Lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

@Composable
fun OnResume(
    onResume: () -> Unit
) {
    OnLifecycleEvent(
        onEvent = { _, event ->
            onResume().takeIf { event == Lifecycle.Event.ON_RESUME }
        }
    )
}