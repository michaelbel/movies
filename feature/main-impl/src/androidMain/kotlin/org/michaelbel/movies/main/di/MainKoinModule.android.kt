package org.michaelbel.movies.main.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.debug.di.debugNotificationClientKoinModule
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.main.MainViewModel
import org.michaelbel.movies.platform.inject.flavorServiceKtorModule
import org.michaelbel.movies.work.di.workKoinModule

actual val mainKoinModule = module {
    includes(
        interactorKoinModule,
        moviesAnalyticsKoinModule,
        flavorServiceKtorModule,
        workKoinModule,
        debugNotificationClientKoinModule
    )
    viewModelOf(::MainViewModel)
}