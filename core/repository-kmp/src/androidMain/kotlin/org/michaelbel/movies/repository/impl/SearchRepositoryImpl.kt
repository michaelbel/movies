package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.ktor.KtorSearchService
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.retrofit.RetrofitSearchService
import org.michaelbel.movies.repository.SearchRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException

/**
 * You can replace [ktorSearchService] with [retrofitSearchService] to use it.
 */
@Singleton
internal class SearchRepositoryImpl @Inject constructor(
    private val retrofitSearchService: RetrofitSearchService,
    private val ktorSearchService: KtorSearchService,
    private val localeController: LocaleController
): SearchRepository {

    override suspend fun searchMoviesResult(query: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty) {
            checkApiKeyNotNullException()
        }

        return ktorSearchService.searchMovies(
            query = query,
            language = localeController.language,
            page = page
        )
    }
}