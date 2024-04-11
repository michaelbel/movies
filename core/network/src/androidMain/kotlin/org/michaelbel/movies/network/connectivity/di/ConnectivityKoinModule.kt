package org.michaelbel.movies.network.connectivity.di

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val connectivityKoinModule = module {
    single { ContextCompat.getSystemService(androidContext(), ConnectivityManager::class.java) as ConnectivityManager }
}