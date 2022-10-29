package org.michaelbel.movies.network.connectivity.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object ConnectivityModule {

    @Provides
    fun provideConnectivityService(
        @ApplicationContext context: Context
    ): ConnectivityManager {
        return ContextCompat.getSystemService(
            context,
            ConnectivityManager::class.java
        ) as ConnectivityManager
    }
}