package org.michaelbel.movies.analytics.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.impl.MoviesAnalyticsImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface MoviesAnalyticsModule {

    @Binds
    @Singleton
    fun provideMoviesAnalytics(analytics: MoviesAnalyticsImpl): MoviesAnalytics
}