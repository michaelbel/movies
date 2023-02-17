package org.michaelbel.movies.domain.interactor.impl

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.domain.repository.MovieRepository
import org.michaelbel.movies.domain.usecase.DelayUseCase
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.entities.MovieData

internal class MovieInteractorImpl @Inject constructor(
    @Dispatcher(MoviesDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val movieRepository: MovieRepository,
    private val delayUseCase: DelayUseCase
): MovieInteractor {

    override suspend fun movieList(list: String, page: Int): Pair<List<MovieData>, Int> {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatcher) {
            movieRepository.movieList(list, page)
        }
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatcher) {
            movieRepository.movieDetails(movieId)
        }
    }
}