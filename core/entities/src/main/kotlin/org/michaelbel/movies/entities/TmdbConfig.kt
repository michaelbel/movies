package org.michaelbel.movies.entities

import java.util.Locale

val tmdbApiKey: String
    get() = BuildConfig.TMDB_API_KEY

val language: String
    get() = Locale.getDefault().language