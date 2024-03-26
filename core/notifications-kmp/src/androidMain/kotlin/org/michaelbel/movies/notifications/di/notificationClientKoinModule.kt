package org.michaelbel.movies.notifications.di

import org.koin.dsl.module
import org.michaelbel.movies.notifications.NotificationClient

val notificationClientKoinModule = module {
    single { NotificationClient(get(), get()) }
}