package org.michaelbel.movies.work.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.work.WorkManagerInteractor
import org.michaelbel.movies.work.impl.WorkManagerInteractorImpl

actual val workManagerInteractorKoinModule = module {
    singleOf(::WorkManagerInteractorImpl) { bind<WorkManagerInteractor>() }
}