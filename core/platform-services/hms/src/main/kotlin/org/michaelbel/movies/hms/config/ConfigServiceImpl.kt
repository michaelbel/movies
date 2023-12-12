package org.michaelbel.movies.hms.config

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.platform.main.config.ConfigService

internal class ConfigServiceImpl @Inject constructor(): ConfigService {

    override fun fetchAndActivate() {}

    override fun getBooleanFlow(name: String): Flow<Boolean> {
        return flowOf(false)
    }
}