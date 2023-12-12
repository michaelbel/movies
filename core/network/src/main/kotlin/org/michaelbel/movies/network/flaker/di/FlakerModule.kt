package org.michaelbel.movies.network.flaker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.rotbolt.flakerokhttpcore.FlakerInterceptor

@Module
@InstallIn(SingletonComponent::class)
internal object FlakerModule {

    @Provides
    fun provideFlakerInterceptor(): FlakerInterceptor = FlakerInterceptor.Builder().build()
}