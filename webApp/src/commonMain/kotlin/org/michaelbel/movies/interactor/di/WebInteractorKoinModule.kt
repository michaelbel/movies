package org.michaelbel.movies.interactor.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.AboutInteractor
import org.michaelbel.movies.interactor.impl.AboutInteractorImpl

val webInteractorKoinModule = module {
    includes(
        browserInteractorKoinModule
    )
    singleOf(::AboutInteractorImpl) { bind<AboutInteractor>() }
}

expect val browserInteractorKoinModule: Module
