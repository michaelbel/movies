package org.michaelbel.movies.network.connectivity.di

import org.koin.dsl.module
import org.michaelbel.movies.network.connectivity.NetworkManager

val networkManagerKoinModule = module {
    includes(
        connectivityKoinModule
    )
    single { NetworkManager(get()) }
}