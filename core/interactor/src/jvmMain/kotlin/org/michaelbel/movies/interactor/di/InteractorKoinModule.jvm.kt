package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.SettingsUiInteractor
import org.michaelbel.movies.interactor.impl.AboutInteractorImpl
import org.michaelbel.movies.interactor.impl.LocaleInteractorImpl
import org.michaelbel.movies.interactor.impl.SettingsUiInteractorImpl
import org.michaelbel.movies.persistence.database.di.moviesDatabaseKoinModule

actual val localeInteractorKoinModule = module {
    includes(
        dispatchersKoinModule,
        moviesDatabaseKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::LocaleInteractorImpl) { bind<LocaleInteractor>() }
}

actual val aboutInteractorKoinModule = module {
    singleOf(::AboutInteractorImpl) { bind<AboutInteractor>() }
}

actual val settingsUiInteractorKoinModule = module {
    singleOf(::SettingsUiInteractorImpl) { bind<SettingsUiInteractor>() }
}