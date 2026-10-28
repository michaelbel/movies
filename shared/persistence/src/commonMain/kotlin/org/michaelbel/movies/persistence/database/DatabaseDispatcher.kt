package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Default dispatcher for Room queries: `Dispatchers.IO` on JVM, Android and iOS, `Dispatchers.Default` on web
 * where `Dispatchers.IO` is not available.
 */
expect val databaseDispatcher: CoroutineDispatcher
