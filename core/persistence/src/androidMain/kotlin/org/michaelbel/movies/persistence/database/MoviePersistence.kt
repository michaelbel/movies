@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.ktx.movieDb

actual class MoviePersistence internal constructor(
    private val movieDao: MovieDao
) {

    actual fun pagingSource(movieList: String): PagingSource<Int, MoviePojo> {
        return movieDao.pagingSource(movieList)
    }

    actual fun moviesFlow(movieList: String, limit: Int): Flow<List<MoviePojo>> {
        return movieDao.moviesFlow(movieList, limit)
    }

    actual suspend fun movies(movieList: String, limit: Int): List<MoviePojo> {
        return movieDao.movies(movieList, limit)
    }

    actual suspend fun moviesMini(movieList: String, limit: Int): List<MovieDbMini> {
        return movieDao.moviesMini(movieList, limit)
    }

    actual suspend fun insertMovies(movies: List<MoviePojo>) {
        movieDao.insertMovies(movies.map(MoviePojo::movieDb))
    }

    actual suspend fun insertMovie(movie: MoviePojo) {
        movieDao.insertMovie(movie.movieDb)
    }

    actual suspend fun removeMovies(movieList: String) {
        movieDao.removeMovies(movieList)
    }

    actual suspend fun removeMovie(movieList: String, movieId: Int) {
        movieDao.removeMovie(movieList, movieId)
    }

    actual suspend fun movieById(pagingKey: String, movieId: Int): MoviePojo? {
        return movieDao.movieById(pagingKey, movieId)
    }

    actual suspend fun maxPosition(movieList: String): Int? {
        return movieDao.maxPosition(movieList)
    }

    actual suspend fun isEmpty(movieList: String): Boolean {
        return movieDao.isEmpty(movieList)
    }

    actual suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {
        movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}