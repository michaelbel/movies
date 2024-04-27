@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

expect class MoviePersistence {

    fun pagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo>

    fun moviesFlow(
        pagingKey: PagingKey,
        limit: Limit
    ): Flow<List<MoviePojo>>

    suspend fun movies(
        pagingKey: PagingKey,
        limit: Limit
    ): List<MoviePojo>

    suspend fun moviesMini(
        pagingKey: PagingKey,
        limit: Limit
    ): List<MovieDbMini>

    suspend fun insertMovies(
        movies: List<MoviePojo>
    )

    suspend fun insertMovie(
        movie: MoviePojo
    )

    suspend fun removeMovies(
        pagingKey: PagingKey
    )

    suspend fun removeMovie(
        pagingKey: PagingKey,
        movieId: MovieId
    )

    suspend fun movieById(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo?

    suspend fun maxPosition(
        pagingKey: PagingKey
    ): Int?

    suspend fun isEmpty(
        pagingKey: PagingKey
    ): Boolean

    suspend fun updateMovieColors(
        movieId: MovieId,
        containerColor: Int,
        onContainerColor: Int
    )
}