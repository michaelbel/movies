package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.TRAKT_DATA_STORE_NAME

actual val traktDataStoreKoinModule = module {
    single<DataStore<Preferences>>(named("trakt")) {
        createDataStore(
            producePath = { androidContext().filesDir.resolve(TRAKT_DATA_STORE_NAME).absolutePath }
        )
    }
}
