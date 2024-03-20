package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.SearchNetworkService
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SearchRepositoryImpl @Inject constructor(
    private val searchNetworkService: SearchNetworkService,
    private val localeController: LocaleController
): SearchRepository {

    override suspend fun searchMoviesResult(query: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty) {
            checkApiKeyNotNullException()
        }

        return searchNetworkService.searchMovies(
            query = query,
            language = localeController.language,
            page = page
        )
    }
}