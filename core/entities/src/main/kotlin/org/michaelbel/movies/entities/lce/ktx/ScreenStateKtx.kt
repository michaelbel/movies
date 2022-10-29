package org.michaelbel.movies.entities.lce.ktx

import org.michaelbel.movies.entities.lce.ScreenState

val ScreenState.isFailure: Boolean
    get() = this is ScreenState.Failure

val ScreenState.throwable: Throwable
    get() = (this as ScreenState.Failure).throwable