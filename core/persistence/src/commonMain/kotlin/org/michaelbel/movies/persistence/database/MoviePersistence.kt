@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

expect class MoviePersistence {

    fun pagingSource(movieList: String): PagingSource<Int, MoviePojo>

    fun moviesFlow(movieList: String, limit: Int): Flow<List<MoviePojo>>

    suspend fun movies(movieList: String, limit: Int): List<MoviePojo>

    suspend fun moviesMini(movieList: String, limit: Int): List<MovieDbMini>

    suspend fun insertMovies(movies: List<MoviePojo>)

    suspend fun insertMovie(movie: MoviePojo)

    suspend fun removeMovies(movieList: String)

    suspend fun removeMovie(movieList: String, movieId: Int)

    suspend fun movieById(pagingKey: String, movieId: Int): MoviePojo?

    suspend fun maxPosition(movieList: String): Int?

    suspend fun isEmpty(movieList: String): Boolean

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)
}