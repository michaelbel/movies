package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import okio.Path.Companion.toPath
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME

internal actual val dataStoreKoinModule = module {
    single {
        PreferenceDataStoreFactory.createWithPath(
            migrations = emptyList(),
            produceFile = { DATA_STORE_NAME.toPath() }
        )
    }
}