package org.michaelbel.movies.interactor

import androidx.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.usecase.DelayUseCase
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository

@Singleton
internal class MovieInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val movieRepository: MovieRepository,
    private val delayUseCase: DelayUseCase
): MovieInteractor {

    override fun moviesPagingSource(movieList: String): PagingSource<Int, MovieDb> {
        return movieRepository.moviesPagingSource(movieList)
    }

    override suspend fun backdropPosition(movieId: Int): Int {
        return movieRepository.backdropPosition(movieId)
    }

    override suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse> {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatchers.io) {
            movieRepository.moviesResult(movieList, page)
        }
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        delay(delayUseCase.networkRequestDelay())

        return withContext(dispatchers.io) {
            movieRepository.movieDetails(movieId)
        }
    }

    override suspend fun removeAllMovies(movieList: String) {
        return withContext(dispatchers.io) {
            movieRepository.removeAllMovies(movieList)
        }
    }

    override suspend fun insertAllMovies(movieList: String, movies: List<MovieResponse>) {
        return withContext(dispatchers.io) {
            movieRepository.insertAllMovies(movieList, movies)
        }
    }

    override suspend fun page(movieList: String): Int? {
        return withContext(dispatchers.io) {
            movieRepository.page(movieList)
        }
    }

    override suspend fun removePagingKey(movieList: String) {
        return withContext(dispatchers.io) {
            movieRepository.removePagingKey(movieList)
        }
    }

    override suspend fun insertPagingKey(movieList: String, page: Int) {
        return withContext(dispatchers.io) {
            movieRepository.insertPagingKey(movieList, page)
        }
    }
}