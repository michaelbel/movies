package org.michaelbel.movies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.michaelbel.movies.common.exceptions.PageEmptyException

fun Modifier.clickableWithoutRipple(
    block: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickableWithoutRipple"
        value = block
    }
) {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = { block() }
    )
}

val <T: Any> LazyPagingItems<T>.isLoading: Boolean
    get() = refreshState is LoadState.Loading

val <T: Any> LazyPagingItems<T>.isFailure: Boolean
    get() = refreshState is LoadState.Error && itemCount == 0

val <T: Any> LazyPagingItems<T>.isPagingLoading: Boolean
    get() = itemCount > 0 && (appendState is LoadState.Loading || isLoading)

val <T: Any> LazyPagingItems<T>.isPagingFailure: Boolean
    get() = itemCount > 0 && (appendState is LoadState.Error && appendThrowable !is PageEmptyException || refreshState is LoadState.Error && refreshThrowable !is PageEmptyException)

val <T: Any> LazyPagingItems<T>.refreshThrowable: Throwable
    get() = (refreshState as LoadState.Error).error

val <T: Any> LazyPagingItems<T>.appendThrowable: Throwable
    get() = (appendState as LoadState.Error).error

private val <T: Any> LazyPagingItems<T>.refreshState: LoadState
    get() = loadState.mediator?.refresh ?: loadState.refresh

private val <T: Any> LazyPagingItems<T>.appendState: LoadState
    get() = loadState.mediator?.append ?: loadState.append

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

@Composable
private fun OnLifecycleEvent(
    onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit
) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}
