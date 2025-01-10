package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.network.SearchNetworkService
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.Query
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException

internal class SearchRepositoryImpl(
    private val searchNetworkService: SearchNetworkService
): SearchRepository {

    override suspend fun searchMoviesResult(
        query: Query,
        language: String,
        page: Page
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