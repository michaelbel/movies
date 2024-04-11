package org.michaelbel.movies.network.config

sealed interface ScreenState {

    data object Loading: ScreenState

    data class Content<T>(val data: T): ScreenState

    data class Failure(val throwable: Throwable): ScreenState
}