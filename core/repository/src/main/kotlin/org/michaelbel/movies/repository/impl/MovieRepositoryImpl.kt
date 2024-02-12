package org.michaelbel.movies.repository.impl

import androidx.paging.PagingSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.exceptions.MovieDetailsException
import org.michaelbel.movies.common.localization.LocaleController
import org.michaelbel.movies.network.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.service.movie.MovieService
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException
import org.michaelbel.movies.repository.ktx.mapToMovieDb

@Singleton
internal class MovieRepositoryImpl @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val localeController: LocaleController
): MovieRepository {

    override fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb> {
        return movieDao.pagingSource(pagingKey)
    }

    override fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>> {
        return movieDao.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty && movieDao.isEmpty(MovieDb.MOVIES_LOCAL_LIST)) {
            checkApiKeyNotNullException()
        }

        return movieService.movies(
            list = movieList,
            language = localeController.language,
            page = page
        )
    }

    override suspend fun movie(pagingKey: String, movieId: Int): MovieDb {
        return movieDao.movieById(pagingKey, movieId).orEmpty
    }

    override suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb {
        return try {
            movieDao.movieById(pagingKey, movieId) ?: movieService.movie(movieId, localeController.language).mapToMovieDb
        } catch (e: Exception) {
            throw MovieDetailsException
        }
    }

    override suspend fun removeMovies(pagingKey: String) {
        movieDao.removeMovies(pagingKey)
    }

    override suspend fun removeMovie(pagingKey: String, movieId: Int) {
        movieDao.removeMovie(pagingKey, movieId)
    }

    override suspend fun insertMovies(pagingKey: String, page: Int, movies: List<MovieResponse>) {
        val maxPosition = movieDao.maxPosition(pagingKey) ?: 0
        val moviesDb = movies.mapIndexed { index, movieResponse ->
            movieResponse.mapToMovieDb(
                movieList = pagingKey,
                page = page,
                position = if (maxPosition == 0) index else maxPosition.plus(index).plus(1)
            )
        }
        movieDao.insertMovies(moviesDb)
    }

    override suspend fun insertMovie(pagingKey: String, movie: MovieDb) {
        val maxPosition = movieDao.maxPosition(pagingKey) ?: 0
        movieDao.insertMovie(
            movie.copy(
                movieList = pagingKey,
                dateAdded = System.currentTimeMillis(),
                position = maxPosition.plus(1)
            )
        )
    }

    override suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {
        movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}