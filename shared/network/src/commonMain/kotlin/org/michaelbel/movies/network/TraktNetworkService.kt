package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorTraktService
import org.michaelbel.movies.network.model.TraktMovieResponse
import org.michaelbel.movies.network.model.TraktMoviesResult

class TraktNetworkService(
    private val ktorTraktService: KtorTraktService
) {

    suspend fun movie(traktId: Int): TraktMovieResponse {
        return ktorTraktService.movie(traktId)
    }

    suspend fun search(query: String, page: Int, limit: Int): TraktMoviesResult {
        return ktorTraktService.search(query, page, limit)
    }

    suspend fun movies(list: String, page: Int, limit: Int): TraktMoviesResult {
        return ktorTraktService.movies(list, page, limit)
    }
}
