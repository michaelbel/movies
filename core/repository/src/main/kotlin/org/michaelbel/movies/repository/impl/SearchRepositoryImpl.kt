package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.service.search.SearchService
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException

@Singleton
internal class SearchRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val localeController: LocaleController
): SearchRepository {

    override suspend fun searchMoviesResult(query: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty) {
            checkApiKeyNotNullException()
        }

        return searchService.searchMovies(
            query = query,
            language = localeController.language,
            page = page
        )
    }
}