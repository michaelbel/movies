package org.michaelbel.movies.repository.ktx

import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty

internal actual fun checkApiKeyNotNullException() {
    if (isTmdbApiKeyEmpty) throw ApiKeyNotNullException
}