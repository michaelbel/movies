package org.michaelbel.movies.repository.ktx

import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty

internal fun checkApiKeyNotNullException() {
    if (isTmdbApiKeyEmpty) throw ApiKeyNotNullException
}