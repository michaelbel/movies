package org.michaelbel.movies.notifications.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.notifications.NotificationClient
import org.michaelbel.movies.notifications.impl.NotificationClientImpl

actual val notificationClientKoinModule = module {
    singleOf(::NotificationClientImpl) { bind<NotificationClient>() }
}