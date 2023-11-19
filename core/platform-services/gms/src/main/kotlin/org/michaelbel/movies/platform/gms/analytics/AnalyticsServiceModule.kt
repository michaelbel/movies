package org.michaelbel.movies.platform.gms.analytics

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.analytics.AnalyticsService

@Module
@InstallIn(SingletonComponent::class)
internal interface AnalyticsServiceModule {

    @Binds
    @Singleton
    fun provideAnalyticsService(service: AnalyticsServiceImpl): AnalyticsService
}