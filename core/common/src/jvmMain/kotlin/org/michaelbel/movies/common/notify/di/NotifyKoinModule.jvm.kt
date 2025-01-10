package org.michaelbel.movies.common.notify.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.notify.NotifyManager
import org.michaelbel.movies.common.notify.impl.NotifyManagerImpl

actual val notifyKoinModule = module {
    singleOf(::NotifyManagerImpl) { bind<NotifyManager>() }
}