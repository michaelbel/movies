@file:OptIn(ExperimentalForeignApi::class)

package org.michaelbel.movies.persistence.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import org.michaelbel.movies.persistence.datastore.DATA_STORE_NAME
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

internal actual val dataStoreKoinModule = module {
    single<DataStore<Preferences>> {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$DATA_STORE_NAME"
            }
        )
    }
}