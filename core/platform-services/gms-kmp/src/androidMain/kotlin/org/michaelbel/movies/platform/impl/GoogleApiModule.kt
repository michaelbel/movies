package org.michaelbel.movies.platform.impl

import com.google.android.gms.common.GoogleApiAvailability
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GoogleApiModule {

    @Provides
    @Singleton
    fun provideGoogleApiAvailability(): GoogleApiAvailability = GoogleApiAvailability.getInstance()
}