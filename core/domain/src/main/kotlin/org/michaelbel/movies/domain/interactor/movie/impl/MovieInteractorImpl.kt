package org.michaelbel.movies.domain.interactor.movie.impl

import androidx.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.coroutines.Dispatcher
import org.michaelbel.movies.common.coroutines.MoviesDispatchers
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.interactor.movie.MovieInteractor
import org.michaelbel.movies.domain.repository.movie.MovieRepository
import org.michaelbel.movies.domain.usecase.DelayUseCase
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

@Singleton
internal class MovieInteractorImpl @Inject constructor(
    @Dispatcher(MoviesDispatchers.IO) private val dispatcher: CoroutineDispatcher,
    private val movieRepository: MovieRepository,
    private val delayUseCase: DelayUseCase
): MovieInteractor {

    override fun moviesPagingSource(movieList: String): PagingSource<Int, MovieDb> {
        return movieRepository.moviesPagingSource(movieList)
    }

    override suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse> {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatcher) {
            movieRepository.moviesResult(movieList, page)
        }
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatcher) {
            movieRepository.movieDetails(movieId)
        }
    }

    override suspend fun removeAllMovies(movieList: String) {
        return withContext(dispatcher) {
            movieRepository.removeAllMovies(movieList)
        }
    }

    override suspend fun insertAllMovies(movieList: String, movies: List<MovieResponse>) {
        return withContext(dispatcher) {
            movieRepository.insertAllMovies(movieList, movies)
        }
    }

    override suspend fun page(movieList: String): Int? {
        return withContext(dispatcher) {
            movieRepository.page(movieList)
        }
    }

    override suspend fun removePagingKey(movieList: String) {
        return withContext(dispatcher) {
            movieRepository.removePagingKey(movieList)
        }
    }

    override suspend fun insertPagingKey(movieList: String, page: Int) {
        return withContext(dispatcher) {
            movieRepository.insertPagingKey(movieList, page)
        }
    }
}