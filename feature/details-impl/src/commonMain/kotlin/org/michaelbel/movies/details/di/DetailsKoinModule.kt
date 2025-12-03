package org.michaelbel.movies.details.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.connectivityKoinModule
import org.michaelbel.movies.ui.navigation.DetailsDestination

val detailsKoinModule = module {
    includes(
        interactorKoinModule,
        connectivityKoinModule
    )
    viewModel { (destination: DetailsDestination) ->
        DetailsViewModel(destination, get(), get())
    }
}