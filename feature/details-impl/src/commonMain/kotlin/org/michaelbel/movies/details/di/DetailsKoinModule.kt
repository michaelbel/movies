package org.michaelbel.movies.details.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.connectivityKoinModule

val detailsKoinModule = module {
    includes(
        interactorKoinModule,
        connectivityKoinModule
    )
    viewModelOf(::DetailsViewModel)
}