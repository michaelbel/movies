package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME
import org.michaelbel.movies.persistence.datastore.SHARED_PREFERENCES_NAME

internal actual val dataStoreKoinModule = module {
    single {
        PreferenceDataStoreFactory.createWithPath(
            migrations = listOf(SharedPreferencesMigration(androidContext(), SHARED_PREFERENCES_NAME)),
            produceFile = { androidContext().filesDir.resolve(DATA_STORE_NAME).absolutePath.toPath() }
        )
    }
}