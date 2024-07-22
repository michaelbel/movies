package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.movieDb
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class MoviePersistence internal constructor(
    private val moviesDatabase: MoviesDatabase
) {

    fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>> {
        return moviesDatabase.movieDao.moviesFlow(pagingKey, limit)
    }

    suspend fun movies(pagingKey: PagingKey, limit: Limit): List<MoviePojo> {
        return moviesDatabase.movieDao.movies(pagingKey, limit)
    }

    suspend fun moviesMini(pagingKey: PagingKey, limit: Limit): List<MovieDbMini> {
        return moviesDatabase.movieDao.moviesMini(pagingKey, limit)
    }

    suspend fun insertMovies(movies: List<MoviePojo>) {
        moviesDatabase.movieDao.insertMovies(movies.map(MoviePojo::movieDb))
    }

    suspend fun insertMovie(movie: MoviePojo) {
        moviesDatabase.movieDao.insertMovie(movie.movieDb)
    }

    suspend fun removeMovies(pagingKey: PagingKey) {
        moviesDatabase.movieDao.removeMovies(pagingKey)
    }

    suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        moviesDatabase.movieDao.removeMovie(pagingKey, movieId)
    }

    suspend fun movieById(pagingKey: PagingKey, movieId: MovieId): MoviePojo? {
        return moviesDatabase.movieDao.movieById(pagingKey, movieId)
    }

    suspend fun maxPosition(pagingKey: PagingKey): Int? {
        return moviesDatabase.movieDao.maxPosition(pagingKey)
    }

    suspend fun isEmpty(pagingKey: PagingKey): Boolean {
        return moviesDatabase.movieDao.isEmpty(pagingKey)
    }

    suspend fun updateMovieColors(movieId: MovieId, containerColor: Int, onContainerColor: Int) {
        moviesDatabase.movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}