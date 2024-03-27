package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.MovieDao
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

class MoviePersistence internal constructor(
    private val movieDao: MovieDao
) {

    fun pagingSource(movieList: String): PagingSource<Int, MovieDb> {
        return movieDao.pagingSource(movieList)
    }

    fun moviesFlow(movieList: String, limit: Int): Flow<List<MovieDb>> {
        return movieDao.moviesFlow(movieList, limit)
    }

    suspend fun movies(movieList: String, limit: Int): List<MovieDb> {
        return movieDao.movies(movieList, limit)
    }

    suspend fun moviesMini(movieList: String, limit: Int): List<MovieDbMini> {
        return movieDao.moviesMini(movieList, limit)
    }

    suspend fun insertMovies(movies: List<MovieDb>) {
        movieDao.insertMovies(movies)
    }

    suspend fun insertMovie(movie: MovieDb) {
        movieDao.insertMovie(movie)
    }

    suspend fun removeMovies(movieList: String) {
        movieDao.removeMovies(movieList)
    }

    suspend fun removeMovie(movieList: String, movieId: Int) {
        movieDao.removeMovie(movieList, movieId)
    }

    suspend fun movieById(pagingKey: String, movieId: Int): MovieDb? {
        return movieDao.movieById(pagingKey, movieId)
    }

    suspend fun maxPosition(movieList: String): Int? {
        return movieDao.maxPosition(movieList)
    }

    suspend fun isEmpty(movieList: String): Boolean {
        return movieDao.isEmpty(movieList)
    }

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {
        movieDao.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}