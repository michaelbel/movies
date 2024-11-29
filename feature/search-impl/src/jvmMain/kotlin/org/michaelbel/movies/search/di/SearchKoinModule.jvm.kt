package org.michaelbel.movies.search.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.search.SearchViewModel

actual val searchKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { SearchViewModel(get()) }
}