package org.michaelbel.movies.network.config

import org.michaelbel.movies.network.BuildKonfig

actual val tmdbApiKey: String
    get() = BuildKonfig.TMDB_API_KEY

actual val isTmdbApiKeyEmpty: Boolean
    get() = tmdbApiKey.isEmpty() || tmdbApiKey == "null"