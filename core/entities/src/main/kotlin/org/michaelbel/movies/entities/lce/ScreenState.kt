package org.michaelbel.movies.entities.lce

sealed interface ScreenState {

    object Loading: ScreenState

    data class Content<T>(val data: T): ScreenState

    data class Failure(val throwable: Throwable): ScreenState
}