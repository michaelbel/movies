package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.impl.AboutInteractorImpl
import org.michaelbel.movies.interactor.impl.BrowserInteractorImpl
import org.michaelbel.movies.interactor.impl.LocaleInteractorImpl
import org.michaelbel.movies.interactor.impl.UiInteractorImpl

actual val localeInteractorKoinModule = module {
    includes(
        dispatchersKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::LocaleInteractorImpl) { bind<LocaleInteractor>() }
}

actual val aboutInteractorKoinModule = module {
    singleOf(::AboutInteractorImpl) { bind<AboutInteractor>() }
}

actual val browserInteractorKoinModule = module {
    singleOf(::BrowserInteractorImpl) { bind<BrowserInteractor>() }
}

actual val uiInteractorKoinModule = module {
    singleOf(::UiInteractorImpl) { bind<UiInteractor>() }
}
