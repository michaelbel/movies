package org.michaelbel.movies.network.config

import org.michaelbel.movies.network.BuildConfig

private val tmdbApiKey: String
    get() = BuildConfig.TMDB_API_KEY

actual val isTmdbApiKeyEmpty: Boolean
    get() = tmdbApiKey.isEmpty() || tmdbApiKey == "null"