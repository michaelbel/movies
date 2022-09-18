package org.michaelbel.moviemade.dependencies

import org.gradle.api.artifacts.dsl.DependencyHandler
import org.michaelbel.moviemade.extensions.api

/**
 * DataStore
 *
 * @see <a href="https://developer.android.com/jetpack/androidx/releases/datastore">DataStore</a>
 */

private const val DataStoreVersion = "1.0.0"

private const val DataStoreCore = "androidx.datastore:datastore-core:$DataStoreVersion"
private const val DataStorePreferences = "androidx.datastore:datastore-preferences:$DataStoreVersion"
private const val DataStorePreferencesCore = "androidx.datastore:datastore-preferences-core:$DataStoreVersion"

fun DependencyHandler.apiDataStoreDependencies() {
    api(DataStoreCore)
    api(DataStorePreferences)
    api(DataStorePreferencesCore)
}