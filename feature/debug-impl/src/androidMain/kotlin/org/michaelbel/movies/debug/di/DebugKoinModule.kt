package org.michaelbel.movies.debug.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.debug.DebugViewModel

val debugKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::DebugViewModel)
}