package org.michaelbel.movies.persistence.datastore.ktx

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun dataStorePreferences(
    producePath: () -> String,
    migrations: List<DataMigration<Preferences>> = emptyList()
): DataStore<Preferences>