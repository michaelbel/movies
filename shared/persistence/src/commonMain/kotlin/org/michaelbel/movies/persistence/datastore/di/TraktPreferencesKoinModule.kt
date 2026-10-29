package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.TraktPreferences

val traktPreferencesKoinModule = module {
    includes(
        traktDataStoreKoinModule
    )
    single { TraktPreferences(get<DataStore<Preferences>>(named("trakt"))) }
}
