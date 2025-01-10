package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.MoviesPreferences

val moviesPreferencesKoinModule = module {
    includes(
        dataStoreKoinModule
    )
    single { MoviesPreferences(get<DataStore<Preferences>>()) }
}