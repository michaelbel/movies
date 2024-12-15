package org.michaelbel.movies.network.connectivity.di

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.impl.NetworkManagerImpl

actual val connectivityKoinModule = module {
    single { ContextCompat.getSystemService(androidContext(), ConnectivityManager::class.java) as ConnectivityManager }
    singleOf(::NetworkManagerImpl) { bind<NetworkManager>() }
}