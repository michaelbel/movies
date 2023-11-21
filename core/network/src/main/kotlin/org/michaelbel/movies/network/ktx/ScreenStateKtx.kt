package org.michaelbel.movies.network.ktx

import org.michaelbel.movies.network.ScreenState

val ScreenState.isFailure: Boolean
    get() = this is ScreenState.Failure

val ScreenState.throwable: Throwable
    get() = (this as ScreenState.Failure).throwable