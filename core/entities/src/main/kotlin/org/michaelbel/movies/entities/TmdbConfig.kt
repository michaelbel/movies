package org.michaelbel.movies.entities

const val TMDB_MOVIE_URL = "https://themoviedb.org/movie/%d"

val tmdbApiKey: String
    get() = BuildConfig.TMDB_API_KEY