package org.michaelbel.movies.debug.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.debug.DebugNotificationInteractor
import org.michaelbel.movies.debug.impl.DebugNotificationInteractorImpl

actual val debugNotificationClientKoinModule = module {
    singleOf(::DebugNotificationInteractorImpl) { bind<DebugNotificationInteractor>() }
}