package org.michaelbel.movies.common.dispatchers.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.common.dispatchers.impl.MoviesDispatchersImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DispatchersModule {

    @Binds
    fun provideDispatchers(
        dispatchers: MoviesDispatchersImpl
    ): MoviesDispatchers
}