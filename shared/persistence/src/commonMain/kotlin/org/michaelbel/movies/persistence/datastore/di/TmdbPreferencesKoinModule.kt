package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.TmdbPreferences

val tmdbPreferencesKoinModule = module {
    includes(
        tmdbDataStoreKoinModule
    )
    single { TmdbPreferences(get<DataStore<Preferences>>(named("tmdb"))) }
}
