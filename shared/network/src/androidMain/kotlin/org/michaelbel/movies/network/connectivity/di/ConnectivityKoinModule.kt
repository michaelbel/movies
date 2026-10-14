package org.michaelbel.movies.network.connectivity.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.impl.BackendConnectivityInterceptor
import org.michaelbel.movies.network.connectivity.impl.MoviesConnectivityMonitor
import org.michaelbel.movies.network.connectivity.impl.NetworkManagerImpl

actual val connectivityKoinModule = module {
    single { MoviesConnectivityMonitor(androidContext()) }
    singleOf(::BackendConnectivityInterceptor)
    singleOf(::NetworkManagerImpl) { bind<NetworkManager>() }
}
