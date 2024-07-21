package org.michaelbel.movies.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal actual val uiDispatcher: CoroutineDispatcher = Dispatchers.Unconfined