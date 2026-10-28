package org.michaelbel.movies.feed.ktx

import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException

fun Throwable.toUserMessage(): String {
    return when (this) {
        is ApiKeyNotNullException -> "TMDB API key is missing. Set TMDB_API_KEY before starting the web app."
        else -> message ?: this::class.simpleName.orEmpty()
    }
}
