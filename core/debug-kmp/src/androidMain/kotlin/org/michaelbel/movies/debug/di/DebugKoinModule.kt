package org.michaelbel.movies.debug.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.debug.DebugViewModel

val debugKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::DebugViewModel)
}