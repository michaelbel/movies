package org.michaelbel.movies.di

import org.koin.dsl.module
import org.michaelbel.movies.MainViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule

internal val mainKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { MainViewModel(get()) }
}