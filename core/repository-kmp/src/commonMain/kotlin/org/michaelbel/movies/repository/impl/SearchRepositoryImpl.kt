package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.network.SearchNetworkService
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException

internal class SearchRepositoryImpl(
    private val searchNetworkService: SearchNetworkService
): SearchRepository {

    override suspend fun searchMoviesResult(
        query: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty) {
            checkApiKeyNotNullException()
        }

        return searchNetworkService.searchMovies(
            query = query,
            language = language,
            page = page
        )
    }
}