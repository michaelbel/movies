package org.michaelbel.movies.interactor.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.BrowserInteractor
import org.michaelbel.movies.interactor.impl.BrowserInteractorImpl

actual val browserInteractorKoinModule = module {
    singleOf(::BrowserInteractorImpl) { bind<BrowserInteractor>() }
}
