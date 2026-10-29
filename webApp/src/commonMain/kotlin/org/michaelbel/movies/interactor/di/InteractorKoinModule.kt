package org.michaelbel.movies.interactor.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.domain.usecase.di.useCaseKoinModule

val interactorKoinModule = module {
    includes(
        dispatchersKoinModule,
        useCaseKoinModule,
        moviesAnalyticsKoinModule,
        localeInteractorKoinModule,
        aboutInteractorKoinModule,
        uiInteractorKoinModule
    )
}

expect val localeInteractorKoinModule: Module
expect val aboutInteractorKoinModule: Module
expect val uiInteractorKoinModule: Module
