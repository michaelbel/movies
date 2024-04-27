@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.ktx.movieDb
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

actual class MoviePersistence internal constructor(
    private val movieDao: MovieDao
) {

    actual fun pagingSource(pagingKey: PagingKey): PagingSource<Int, MoviePojo> {
        return movieDao.pagingSource(pagingKey)
    }

    actual fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>> {
        return movieDao.moviesFlow(pagingKey, limit)
    }

    actual suspend fun movies(pagingKey: PagingKey, limit: Limit): List<MoviePojo> {
        return movieDao.movies(pagingKey, limit)
    }

    actual suspend fun moviesMini(pagingKey: PagingKey, limit: Limit): List<MovieDbMini> {
        return movieDao.moviesMini(pagingKey, limit)
    }

    actual suspend fun insertMovies(movies: List<MoviePojo>) {
        movieDao.insertMovies(movies.map(MoviePojo::movieDb))
    }

    actual suspend fun insertMovie(movie: MoviePojo) {
        movieDao.insertMovie(movie.movieDb)
    }

    actual suspend fun removeMovies(pagingKey: PagingKey) {
        movieDao.removeMovies(pagingKey)
    }

    actual suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        movieDao.removeMovie(pagingKey, movieId)
    }

    actual suspend fun movieById(pagingKey: PagingKey, movieId: MovieId): MoviePojo? {
        return movieDao.movieById(pagingKey, movieId)
    }

    actual suspend fun maxPosition(pagingKey: PagingKey): Int? {
        return movieDao.maxPosition(pagingKey)
    }

    actual suspend fun isEmpty(pagingKey: PagingKey): Boolean {
        return movieDao.isEmpty(pagingKey)
    }

    actual suspend fun updateMovieColors(movieId: MovieId, containerColor: Int, onContainerColor: Int) {
        movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}