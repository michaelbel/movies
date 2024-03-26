package org.michaelbel.movies.persistence.datastore.ktx

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

actual fun dataStorePreferences(
    producePath: () -> String,
    migrations: List<DataMigration<Preferences>>
): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        migrations = migrations,
        produceFile = { producePath().toPath() }
    )
}