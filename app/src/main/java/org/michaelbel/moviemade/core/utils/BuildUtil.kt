package org.michaelbel.moviemade.core.utils

import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY

object BuildUtil {

    fun isEmptyApiKey(): Boolean = TMDB_API_KEY == "null"
}