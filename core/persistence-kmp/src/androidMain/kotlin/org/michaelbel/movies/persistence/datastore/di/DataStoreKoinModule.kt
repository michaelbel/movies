package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.preferences.SharedPreferencesMigration
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME
import org.michaelbel.movies.persistence.datastore.SHARED_PREFERENCES_NAME
import org.michaelbel.movies.persistence.datastore.ktx.dataStorePreferences

val dataStoreKoinModule = module {
    single {
        dataStorePreferences(
            producePath = { androidContext().filesDir.resolve(DATA_STORE_NAME).absolutePath },
            migrations = listOf(SharedPreferencesMigration(androidContext(), SHARED_PREFERENCES_NAME))
        )
    }
}