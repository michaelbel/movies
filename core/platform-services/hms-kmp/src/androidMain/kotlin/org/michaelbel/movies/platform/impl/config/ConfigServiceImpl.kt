package org.michaelbel.movies.platform.impl.config

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.platform.config.ConfigService

class ConfigServiceImpl: ConfigService {

    override fun fetchAndActivate() {}

    override fun getBooleanFlow(name: String): Flow<Boolean> {
        return flowOf(false)
    }
}