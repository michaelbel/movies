package org.michaelbel.movies.core

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.core.ktx.createService
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Api {
        return createService(retrofit)
    }
}