package org.michaelbel.movies.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface MoviesDispatchers {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val immediate: CoroutineDispatcher
}