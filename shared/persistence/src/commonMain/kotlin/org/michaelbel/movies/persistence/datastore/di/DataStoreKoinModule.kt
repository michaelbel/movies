package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module

expect val dataStoreKoinModule: Module

expect val tmdbDataStoreKoinModule: Module

expect val traktDataStoreKoinModule: Module

fun createDataStore(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    migrations: List<DataMigration<Preferences>> = emptyList(),
    producePath: () -> String
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        corruptionHandler = corruptionHandler,
        migrations = migrations,
        produceFile = { producePath().toPath() }
    )
}
