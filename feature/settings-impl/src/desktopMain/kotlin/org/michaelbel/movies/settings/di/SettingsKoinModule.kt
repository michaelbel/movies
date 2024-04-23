package org.michaelbel.movies.settings.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule

val settingsKoinModule = module {
    includes(
        interactorKoinModule
    )
}