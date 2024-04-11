package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorSearchService
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

class SearchNetworkService internal constructor(
    private val ktorSearchService: KtorSearchService
) {

    suspend fun searchMovies(
        query: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        return ktorSearchService.searchMovies(query, language, page)
    }
}