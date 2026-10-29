package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.analytics.di.moviesAnalyticsKoinModule
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.domain.usecase.di.useCaseKoinModule
import org.michaelbel.movies.interactor.AppNotificationInteractor
import org.michaelbel.movies.interactor.LocaleInteractor
import org.michaelbel.movies.interactor.UiInteractor
import org.michaelbel.movies.interactor.impl.AppNotificationInteractorImpl
import org.michaelbel.movies.interactor.impl.LocaleInteractorImpl
import org.michaelbel.movies.interactor.impl.UiInteractorImpl

actual val localeInteractorKoinModule = module {
    includes(
        dispatchersKoinModule,
        moviesAnalyticsKoinModule
    )
    singleOf(::LocaleInteractorImpl) { bind<LocaleInteractor>() }
}

// AboutInteractor is registered by the web app itself: version name and code are generated in the app module (WebAppInfo).
actual val aboutInteractorKoinModule = module {}

actual val uiInteractorKoinModule = module {
    singleOf(::UiInteractorImpl) { bind<UiInteractor>() }
}

actual val appNotificationInteractorKoinModule = module {
    includes(
        useCaseKoinModule
    )
    singleOf(::AppNotificationInteractorImpl) { bind<AppNotificationInteractor>() }
}
