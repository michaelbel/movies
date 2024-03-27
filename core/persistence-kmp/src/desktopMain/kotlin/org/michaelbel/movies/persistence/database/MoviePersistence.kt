@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

actual class MoviePersistence internal constructor() {

    /*fun pagingSource(movieList: String): PagingSource<Int, MoviePojo> {
        return movieDao.pagingSource(movieList)
    }*/

    fun moviesFlow(movieList: String, limit: Int): Flow<List<MoviePojo>> {
        return emptyFlow()
    }

    suspend fun movies(movieList: String, limit: Int): List<MoviePojo> {
        return emptyList()
    }

    suspend fun moviesMini(movieList: String, limit: Int): List<MovieDbMini> {
        return emptyList()
    }

    suspend fun insertMovies(movies: List<MoviePojo>) {}

    suspend fun insertMovie(movie: MoviePojo) {}

    suspend fun removeMovies(movieList: String) {}

    suspend fun removeMovie(movieList: String, movieId: Int) {}

    suspend fun movieById(pagingKey: String, movieId: Int): MoviePojo? {
        return null
    }

    suspend fun maxPosition(movieList: String): Int? {
        return null
    }

    suspend fun isEmpty(movieList: String): Boolean {
        return true
    }

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {}
}