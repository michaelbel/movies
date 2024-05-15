package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.movieDb
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class MoviePersistence internal constructor(
    private val movieDao: MovieDao
) {

    fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>> {
        return movieDao.moviesFlow(pagingKey, limit)
    }

    suspend fun movies(pagingKey: PagingKey, limit: Limit): List<MoviePojo> {
        return movieDao.movies(pagingKey, limit)
    }

    suspend fun moviesMini(pagingKey: PagingKey, limit: Limit): List<MovieDbMini> {
        return movieDao.moviesMini(pagingKey, limit)
    }

    suspend fun insertMovies(movies: List<MoviePojo>) {
        movieDao.insertMovies(movies.map(MoviePojo::movieDb))
    }

    suspend fun insertMovie(movie: MoviePojo) {
        movieDao.insertMovie(movie.movieDb)
    }

    suspend fun removeMovies(pagingKey: PagingKey) {
        movieDao.removeMovies(pagingKey)
    }

    suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        movieDao.removeMovie(pagingKey, movieId)
    }

    suspend fun movieById(pagingKey: PagingKey, movieId: MovieId): MoviePojo? {
        return movieDao.movieById(pagingKey, movieId)
    }

    suspend fun maxPosition(pagingKey: PagingKey): Int? {
        return movieDao.maxPosition(pagingKey)
    }

    suspend fun isEmpty(pagingKey: PagingKey): Boolean {
        return movieDao.isEmpty(pagingKey)
    }

    suspend fun updateMovieColors(movieId: MovieId, containerColor: Int, onContainerColor: Int) {
        movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}