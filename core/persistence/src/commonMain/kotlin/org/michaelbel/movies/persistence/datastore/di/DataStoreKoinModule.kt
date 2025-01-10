package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.core.module.Module

internal expect val dataStoreKoinModule: Module

fun createDataStore(
    migrations: List<DataMigration<Preferences>> = emptyList(),
    producePath: () -> String
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        migrations = migrations,
        produceFile = { producePath().toPath() }
    )
}