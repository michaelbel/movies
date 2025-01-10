package org.michaelbel.movies.main.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.main.mainnav.MainNavViewModel

val mainNavKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::MainNavViewModel)
}