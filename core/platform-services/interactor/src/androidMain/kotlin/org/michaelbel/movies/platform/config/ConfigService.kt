package org.michaelbel.movies.platform.config

import kotlinx.coroutines.flow.Flow

interface ConfigService {

    fun fetchAndActivate()

    fun getBooleanFlow(name: String): Flow<Boolean>
}