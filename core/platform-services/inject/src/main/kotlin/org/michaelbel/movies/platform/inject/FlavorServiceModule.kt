package org.michaelbel.movies.platform.inject

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.analytics.AnalyticsService
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.config.ConfigService
import org.michaelbel.movies.platform.crashlytics.CrashlyticsService
import org.michaelbel.movies.platform.impl.analytics.AnalyticsServiceImpl
import org.michaelbel.movies.platform.impl.app.AppServiceImpl
import org.michaelbel.movies.platform.impl.config.ConfigServiceImpl
import org.michaelbel.movies.platform.impl.crashlytics.CrashlyticsServiceImpl
import org.michaelbel.movies.platform.impl.messaging.MessagingServiceImpl
import org.michaelbel.movies.platform.impl.review.ReviewServiceImpl
import org.michaelbel.movies.platform.impl.update.UpdateServiceImpl
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.review.ReviewService
import org.michaelbel.movies.platform.update.UpdateService

@Module
@InstallIn(SingletonComponent::class)
internal interface FlavorServiceModule {

    @Binds
    @Singleton
    fun provideAnalyticsService(service: AnalyticsServiceImpl): AnalyticsService

    @Binds
    @Singleton
    fun provideAppService(service: AppServiceImpl): AppService

    @Binds
    @Singleton
    fun provideConfigService(service: ConfigServiceImpl): ConfigService

    @Binds
    @Singleton
    fun provideCrashlyticsService(service: CrashlyticsServiceImpl): CrashlyticsService

    @Binds
    @Singleton
    fun provideMessagingService(service: MessagingServiceImpl): MessagingService

    @Binds
    @Singleton
    fun provideReviewService(service: ReviewServiceImpl): ReviewService

    @Binds
    @Singleton
    fun provideUpdateService(service: UpdateServiceImpl): UpdateService
}