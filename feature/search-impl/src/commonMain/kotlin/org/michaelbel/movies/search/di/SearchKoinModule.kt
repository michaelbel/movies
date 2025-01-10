package org.michaelbel.movies.search.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.connectivityKoinModule
import org.michaelbel.movies.search.SearchViewModel2

val searchKoinModule = module {
    includes(
        interactorKoinModule,
        connectivityKoinModule
    )
    viewModelOf(::SearchViewModel2)
}