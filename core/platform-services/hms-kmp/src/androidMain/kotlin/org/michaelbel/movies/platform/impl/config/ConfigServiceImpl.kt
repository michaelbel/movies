package org.michaelbel.movies.platform.impl.config

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.platform.config.ConfigService

class ConfigServiceImpl @Inject constructor(): ConfigService {

    override fun fetchAndActivate() {}

    override fun getBooleanFlow(name: String): Flow<Boolean> {
        return flowOf(false)
    }
}