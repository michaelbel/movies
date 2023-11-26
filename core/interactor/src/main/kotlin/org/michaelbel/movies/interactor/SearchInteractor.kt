package org.michaelbel.movies.interactor

import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

interface SearchInteractor {

    suspend fun searchMoviesResult(query: String, page: Int): Result<MovieResponse>
}