package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorTraktService
import org.michaelbel.movies.network.model.TraktMoviesResult

class TraktNetworkService(
    private val ktorTraktService: KtorTraktService
) {

    suspend fun movies(list: String, page: Int, limit: Int): TraktMoviesResult {
        return ktorTraktService.movies(list, page, limit)
    }
}
