package org.michaelbel.moviemade.core.local

import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY

object BuildUtil {

    fun isEmptyApiKey() = TMDB_API_KEY == "null"
}