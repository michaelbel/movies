package org.michaelbel.movies.main.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.main.MainViewModel

actual val mainKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { MainViewModel(get()) }
}