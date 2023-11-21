package org.michaelbel.movies.network

sealed interface ScreenState {

    data object Loading: ScreenState

    data class Content<T>(val data: T): ScreenState

    data class Failure(val throwable: Throwable): ScreenState
}