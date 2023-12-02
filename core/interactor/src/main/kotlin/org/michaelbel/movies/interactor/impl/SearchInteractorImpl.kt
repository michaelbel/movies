package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.SearchInteractor
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.repository.SearchRepository

@Singleton
internal class SearchInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val searchRepository: SearchRepository
): SearchInteractor {

    override suspend fun searchMoviesResult(query: String, page: Int): Result<MovieResponse> {
        return withContext(dispatchers.io) {
            searchRepository.searchMoviesResult(query, page)
        }
    }
}