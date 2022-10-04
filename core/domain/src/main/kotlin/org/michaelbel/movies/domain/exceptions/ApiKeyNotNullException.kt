package org.michaelbel.movies.domain.exceptions

import org.michaelbel.movies.entities.isApiKeyEmpty

object ApiKeyNotNullException: Exception()

fun checkApiKeyNotNullException() {
    if (isApiKeyEmpty) throw ApiKeyNotNullException
}