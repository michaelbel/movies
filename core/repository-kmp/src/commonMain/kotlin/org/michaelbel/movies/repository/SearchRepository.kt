package org.michaelbel.movies.repository

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

interface SearchRepository {

    suspend fun searchMoviesResult(
        query: String,
        language: String,
        page: Int
    ): Result<MovieResponse>
}