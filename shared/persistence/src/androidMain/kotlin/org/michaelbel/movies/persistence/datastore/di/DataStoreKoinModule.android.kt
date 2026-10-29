package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME

actual val dataStoreKoinModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = { androidContext().filesDir.resolve(DATA_STORE_NAME).absolutePath }
        )
    }
}
