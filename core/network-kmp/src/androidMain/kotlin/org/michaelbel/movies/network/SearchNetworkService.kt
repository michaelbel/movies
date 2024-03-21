@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorSearchService
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.retrofit.RetrofitSearchService
import javax.inject.Inject

/**
 * You can replace [ktorSearchService] with [retrofitSearchService] to use it.
 */
actual class SearchNetworkService @Inject internal constructor(
    private val retrofitSearchService: RetrofitSearchService,
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