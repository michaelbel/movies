package org.michaelbel.movies.debug.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.debug.notification.DebugNotificationClient

val debugNotificationClientKoinModule = module {
    single { DebugNotificationClient(androidContext()) }
}