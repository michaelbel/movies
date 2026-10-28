package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.movieDb
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class MoviePersistence(
    private val moviesDatabase: MoviesDatabase
) {

    fun pagingSource(pagingKey: PagingKey): PagingSource<Int, MoviePojo> {
        return moviesDatabase.movieDao.pagingSource(pagingKey)
    }

    fun movieFlow(pagingKey: PagingKey, movieId: MovieId): Flow<MoviePojo?> {
        return moviesDatabase.movieDao.movieFlow(pagingKey, movieId)
    }

    fun moviesFlow(pagingKey: PagingKey, limit: Limit): Flow<List<MoviePojo>> {
        return moviesDatabase.movieDao.moviesFlow(pagingKey, limit)
    }

    suspend fun movies(pagingKey: PagingKey, limit: Limit): List<MoviePojo> {
        return moviesDatabase.movieDao.movies(pagingKey, limit)
    }

    suspend fun moviesMini(pagingKey: PagingKey, limit: Limit): List<MovieDbMini> {
        return moviesDatabase.movieDao.moviesMini(pagingKey, limit)
    }

    suspend fun upsert(movies: List<MoviePojo>) {
        moviesDatabase.movieDao.upsert(movies.map(MoviePojo::movieDb))
    }

    suspend fun upsert(movie: MoviePojo) {
        moviesDatabase.movieDao.upsert(movie.movieDb)
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

    suspend fun movieById(movieId: MovieId): MoviePojo? {
        return moviesDatabase.movieDao.movieById(movieId)
    }

    suspend fun maxPosition(pagingKey: PagingKey): Int {
        return moviesDatabase.movieDao.maxPosition(pagingKey)
    }

    suspend fun isEmpty(pagingKey: PagingKey): Boolean {
        return moviesDatabase.movieDao.isEmpty(pagingKey)
    }
}
