package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.TMDB_DATA_STORE_NAME

actual val tmdbDataStoreKoinModule = module {
    single<DataStore<Preferences>>(named("tmdb")) {
        createDataStore(
            producePath = { androidContext().filesDir.resolve(TMDB_DATA_STORE_NAME).absolutePath }
        )
    }
}
