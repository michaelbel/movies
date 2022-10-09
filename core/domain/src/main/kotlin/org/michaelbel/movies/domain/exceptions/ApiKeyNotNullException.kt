package org.michaelbel.movies.domain.exceptions

import org.michaelbel.movies.entities.tmdbApiKey

object ApiKeyNotNullException: Exception()

fun checkApiKeyNotNullException() {
    if (tmdbApiKey.isEmpty() || tmdbApiKey == "null") throw ApiKeyNotNullException
}