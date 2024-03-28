package org.michaelbel.movies.persistence.database.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.persistence.database.db.AppDatabase

actual val databaseKoinModule = module {
    single { AppDatabase.getInstance(androidContext()) }
}