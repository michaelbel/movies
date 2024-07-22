package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME
import org.michaelbel.movies.persistence.datastore.SHARED_PREFERENCES_NAME

internal actual val dataStoreKoinModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            migrations = listOf(SharedPreferencesMigration(androidContext(), SHARED_PREFERENCES_NAME)),
            producePath = { androidContext().filesDir.resolve(DATA_STORE_NAME).absolutePath }
        )
    }
}