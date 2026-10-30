package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.domain.usecase.TraktSearchMoviesResultUseCase.Params
import org.michaelbel.movies.network.TraktNetworkService
import org.michaelbel.movies.network.config.TRAKT_PAGE_SIZE
import org.michaelbel.movies.network.config.isTraktClientIdEmpty
import org.michaelbel.movies.network.model.TraktMoviesResult
import org.michaelbel.movies.persistence.database.typealiases.Page

class TraktSearchMoviesResultUseCase(
    private val traktNetworkService: TraktNetworkService,
    dispatchers: SharedDispatchers
): UseCase<Params, TraktMoviesResult>(dispatchers.io) {

    override suspend fun execute(params: Params): TraktMoviesResult {
        if (isTraktClientIdEmpty) {
            throw ApiKeyNotNullException()
        }

        return traktNetworkService.search(params.query, params.page, TRAKT_PAGE_SIZE)
    }

    data class Params(
        val query: String,
        val page: Page
    )
}
