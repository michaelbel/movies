package org.michaelbel.movies.persistence.datastore.di

import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

val moviesPreferencesKoinModule = module {
    includes(
        dataStoreKoinModule
    )
    single { MoviesPreferences(get()) }
}