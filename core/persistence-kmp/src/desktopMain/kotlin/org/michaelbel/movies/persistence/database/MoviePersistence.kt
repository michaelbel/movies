@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

actual class MoviePersistence internal constructor() {

    actual fun pagingSource(movieList: String): PagingSource<Int, MoviePojo> {
        TODO("Not Implemented")
    }

    actual fun moviesFlow(movieList: String, limit: Int): Flow<List<MoviePojo>> {
        return emptyFlow()
    }

    actual suspend fun movies(movieList: String, limit: Int): List<MoviePojo> {
        return emptyList()
    }

    actual suspend fun moviesMini(movieList: String, limit: Int): List<MovieDbMini> {
        return emptyList()
    }

    actual suspend fun insertMovies(movies: List<MoviePojo>) {}

    actual suspend fun insertMovie(movie: MoviePojo) {}

    actual suspend fun removeMovies(movieList: String) {}

    actual suspend fun removeMovie(movieList: String, movieId: Int) {}

    actual suspend fun movieById(pagingKey: String, movieId: Int): MoviePojo? {
        return null
    }

    actual suspend fun maxPosition(movieList: String): Int? {
        return null
    }

    actual suspend fun isEmpty(movieList: String): Boolean {
        return true
    }

    actual suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int) {}
}