package org.michaelbel.movies.domain.exceptions.ktx

import org.michaelbel.movies.domain.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.entities.tmdbApiKey

fun checkApiKeyNotNullException() {
    if (tmdbApiKey.isEmpty() || tmdbApiKey == "null") throw ApiKeyNotNullException
}