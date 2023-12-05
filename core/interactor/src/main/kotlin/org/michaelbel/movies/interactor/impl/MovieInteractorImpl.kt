package org.michaelbel.movies.interactor.impl

import androidx.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.MovieInteractor
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.repository.MovieRepository

@Singleton
internal class MovieInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val movieRepository: MovieRepository
): MovieInteractor {

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return movieRepository.moviesPagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>> {
        return movieRepository.moviesFlow(
            pagingKey = pagingKey,
            limit = limit
        )
    }

    override suspend fun moviesResult(pagingKey: String, page: Int): Result<MovieResponse> {
        return withContext(dispatchers.io) {
            movieRepository.moviesResult(pagingKey, page)
        }
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MovieDb {
        return withContext(dispatchers.io) {
            movieRepository.movie(pagingKey, movieId)
        }
    }

    override suspend fun movieDetails(movieId: Int): Either<MovieDb> {
        return withContext(dispatchers.io) {
            movieRepository.movieDetails(movieId)
        }
    }

    override suspend fun removeMovies(pagingKey: String) {
        return withContext(dispatchers.io) {
            movieRepository.removeMovies(pagingKey)
        }
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        return withContext(dispatchers.io) {
            movieRepository.removeMovie(pagingKey, movieId)
        }
    }

    override suspend fun insertMovies(pagingKey: String, movies: List<MovieResponse>) {
        return withContext(dispatchers.io) {
            movieRepository.insertMovies(pagingKey, movies)
        }
    }

    override suspend fun insertMovie(pagingKey: String, movie: MovieDb) {
        return withContext(dispatchers.io) {
            movieRepository.insertMovie(pagingKey, movie)
        }
    }
}