@file:Suppress(
    "EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)

package org.michaelbel.movies.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

interface MoviesDispatchers {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val immediate: CoroutineDispatcher
}