package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun <T> StateFlow<T>.collectAsStateCommon(
    lifecycleOwner: LifecycleOwner,
    minActiveState: Lifecycle.State,
    context: CoroutineContext
): State<T> = collectAsState()