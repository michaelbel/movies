package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.TMDB_DATA_STORE_NAME
import java.io.File

actual val tmdbDataStoreKoinModule = module {
    single<DataStore<Preferences>>(named("tmdb")) {
        val dataStoreDir = File(System.getProperty("java.io.tmpdir"), "movies")
        dataStoreDir.mkdirs()

        createDataStore(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            producePath = { dataStoreDir.resolve(TMDB_DATA_STORE_NAME).absolutePath }
        )
    }
}
