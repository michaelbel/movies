package org.michaelbel.movies.entities

const val TMDB_TERMS_OF_USE = "https://themoviedb.org/documentation/website/terms-of-use"
const val TMDB_PRIVACY_POLICY = "https://themoviedb.org/privacy-policy"
const val TMDB_MOVIE_URL = "https://themoviedb.org/movie/%d"

val tmdbApiKey: String
    get() = BuildConfig.TMDB_API_KEY

val isTmdbApiKeyEmpty: Boolean
    get() = tmdbApiKey.isEmpty() || tmdbApiKey == "null"