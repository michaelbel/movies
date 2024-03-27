package org.michaelbel.movies.analytics.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.analytics.impl.MoviesAnalyticsImpl

val moviesAnalyticsKoinModule = module {
    singleOf(::MoviesAnalyticsImpl) { bind<MoviesAnalytics>() }
}