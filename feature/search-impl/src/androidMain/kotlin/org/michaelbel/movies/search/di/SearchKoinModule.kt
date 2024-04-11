package org.michaelbel.movies.search.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.networkManagerKoinModule
import org.michaelbel.movies.search.SearchViewModel

val searchKoinModule = module {
    includes(
        interactorKoinModule,
        networkManagerKoinModule
    )
    viewModelOf(::SearchViewModel)
}