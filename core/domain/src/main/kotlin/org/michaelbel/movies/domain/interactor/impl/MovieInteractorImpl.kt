package org.michaelbel.movies.domain.interactor.impl

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.michaelbel.movies.core.coroutines.IoDispatcher
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.entities.MovieDetailsData

class MovieInteractorImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val movieRepository: MovieRepository,
): MovieInteractor {

    override suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int> {
        return movieRepository.movieList(list, page)
    }

    override suspend fun movieDetails(movieId: Long): Either<MovieDetailsData> {
        return withContext(dispatcher) {
            movieRepository.movieDetails(movieId)
        }
    }
}