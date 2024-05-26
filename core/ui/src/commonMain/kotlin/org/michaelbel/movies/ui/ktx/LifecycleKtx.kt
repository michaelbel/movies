package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlinx.coroutines.flow.StateFlow

@Composable
expect fun <T> StateFlow<T>.collectAsStateCommon(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T>