package org.michaelbel.movies.details.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule

actual val detailsKoinModule = module {
    includes(
        interactorKoinModule
    )
}