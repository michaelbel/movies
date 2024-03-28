package org.michaelbel.movies.common.localization.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.common.localization.impl.LocaleControllerImpl

val localeKoinModule = module {
    includes(
        dispatchersKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::LocaleControllerImpl) { bind<LocaleController>() }
}