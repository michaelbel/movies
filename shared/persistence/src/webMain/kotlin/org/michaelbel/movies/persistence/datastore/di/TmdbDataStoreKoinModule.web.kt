package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.core.okio.WebLocalStorage
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesSerializer
import androidx.datastore.preferences.core.emptyPreferences
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.TMDB_DATA_STORE_NAME

actual val tmdbDataStoreKoinModule = module {
    single<DataStore<Preferences>>(named("tmdb")) {
        PreferenceDataStoreFactory.create(
            storage = WebLocalStorage(
                serializer = PreferencesSerializer,
                name = TMDB_DATA_STORE_NAME
            ),
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() }
        )
    }
}
