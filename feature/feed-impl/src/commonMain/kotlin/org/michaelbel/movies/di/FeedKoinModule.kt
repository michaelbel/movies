package org.michaelbel.movies.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule

val feedKoinModule = module {
    includes(
        interactorKoinModule
    )
}