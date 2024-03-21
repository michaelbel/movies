@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.network.config

actual sealed interface ScreenState {

    data object Loading: ScreenState

    data class Content<T>(val data: T): ScreenState

    data class Failure(val throwable: Throwable): ScreenState
}