package org.michaelbel.movies.auth.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule

val authKoinModule = module {
    includes(
        interactorKoinModule
    )
}