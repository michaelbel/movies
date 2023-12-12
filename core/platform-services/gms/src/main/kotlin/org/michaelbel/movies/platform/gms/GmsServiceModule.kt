package org.michaelbel.movies.platform.gms

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.michaelbel.movies.platform.gms.analytics.AnalyticsServiceImpl
import org.michaelbel.movies.platform.gms.app.AppServiceImpl
import org.michaelbel.movies.platform.gms.config.ConfigServiceImpl
import org.michaelbel.movies.platform.gms.crashlytics.CrashlyticsServiceImpl
import org.michaelbel.movies.platform.gms.messaging.MessagingServiceImpl
import org.michaelbel.movies.platform.gms.review.ReviewServiceImpl
import org.michaelbel.movies.platform.gms.update.UpdateServiceImpl
import org.michaelbel.movies.platform.main.analytics.AnalyticsService
import org.michaelbel.movies.platform.main.app.AppService
import org.michaelbel.movies.platform.main.config.ConfigService
import org.michaelbel.movies.platform.main.crashlytics.CrashlyticsService
import org.michaelbel.movies.platform.main.messaging.MessagingService
import org.michaelbel.movies.platform.main.review.ReviewService
import org.michaelbel.movies.platform.main.update.UpdateService

@Module
@InstallIn(SingletonComponent::class)
internal interface GmsServiceModule {

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