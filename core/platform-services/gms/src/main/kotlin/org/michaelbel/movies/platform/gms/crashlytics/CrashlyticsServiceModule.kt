package org.michaelbel.movies.platform.gms.crashlytics

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.main.crashlytics.CrashlyticsService

@Module
@InstallIn(SingletonComponent::class)
internal interface CrashlyticsServiceModule {

    @Binds
    @Singleton
    fun provideCrashlyticsService(service: CrashlyticsServiceImpl): CrashlyticsService
}