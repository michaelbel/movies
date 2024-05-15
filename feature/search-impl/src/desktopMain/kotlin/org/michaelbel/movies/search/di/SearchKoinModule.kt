package org.michaelbel.movies.search.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule

val searchKoinModule = module {
    includes(
        interactorKoinModule
    )
}