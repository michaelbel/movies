package org.michaelbel.movies.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.swing.Swing

internal actual val uiDispatcher: CoroutineDispatcher = Dispatchers.Swing