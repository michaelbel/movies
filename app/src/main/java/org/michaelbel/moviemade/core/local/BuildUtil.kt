package org.michaelbel.moviemade.core.local

import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY

object BuildUtil {
    const val isApiKeyEmpty: Boolean = TMDB_API_KEY == "null"
}