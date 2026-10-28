package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

actual val databaseDispatcher: CoroutineDispatcher = Dispatchers.IO
