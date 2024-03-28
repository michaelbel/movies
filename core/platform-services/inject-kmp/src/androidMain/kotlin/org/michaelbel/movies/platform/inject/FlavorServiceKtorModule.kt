package org.michaelbel.movies.platform.inject

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.platform.analytics.AnalyticsService
import org.michaelbel.movies.platform.app.AppService
import org.michaelbel.movies.platform.config.ConfigService
import org.michaelbel.movies.platform.crashlytics.CrashlyticsService
import org.michaelbel.movies.platform.impl.analytics.AnalyticsServiceImpl
import org.michaelbel.movies.platform.impl.app.AppServiceImpl
import org.michaelbel.movies.platform.impl.config.ConfigServiceImpl
import org.michaelbel.movies.platform.impl.crashlytics.CrashlyticsServiceImpl
import org.michaelbel.movies.platform.impl.firebaseKoinModule
import org.michaelbel.movies.platform.impl.googleApiKoinModule
import org.michaelbel.movies.platform.impl.messaging.MessagingServiceImpl
import org.michaelbel.movies.platform.impl.playKoinModule
import org.michaelbel.movies.platform.impl.review.ReviewServiceImpl
import org.michaelbel.movies.platform.impl.update.UpdateServiceImpl
import org.michaelbel.movies.platform.messaging.MessagingService
import org.michaelbel.movies.platform.review.ReviewService
import org.michaelbel.movies.platform.update.UpdateService

val flavorServiceKtorModule = module {
    includes(
        firebaseKoinModule,
        googleApiKoinModule,
        playKoinModule
    )
    singleOf(::AnalyticsServiceImpl) { bind<AnalyticsService>() }
    singleOf(::AppServiceImpl) { bind<AppService>() }
    singleOf(::ConfigServiceImpl) { bind<ConfigService>() }
    singleOf(::CrashlyticsServiceImpl) { bind<CrashlyticsService>() }
    singleOf(::MessagingServiceImpl) { bind<MessagingService>() }
    singleOf(::ReviewServiceImpl) { bind<ReviewService>() }
    singleOf(::UpdateServiceImpl) { bind<UpdateService>() }
}