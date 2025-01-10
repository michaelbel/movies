package org.michaelbel.movies.repository

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.Query

interface SearchRepository {

    suspend fun searchMoviesResult(
        query: Query,
        language: String,
        page: Page
    ): Result<MovieResponse>
}