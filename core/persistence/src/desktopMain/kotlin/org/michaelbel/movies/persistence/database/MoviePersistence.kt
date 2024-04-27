@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

actual class MoviePersistence internal constructor() {

    actual fun pagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo> {
        TODO("Not Implemented")
    }

    actual fun moviesFlow(
        pagingKey: PagingKey,
        limit: Limit
    ): Flow<List<MoviePojo>> {
        return emptyFlow()
    }

    actual suspend fun movies(
        pagingKey: PagingKey,
        limit: Limit
    ): List<MoviePojo> {
        return emptyList()
    }

    actual suspend fun moviesMini(
        pagingKey: PagingKey,
        limit: Limit
    ): List<MovieDbMini> {
        return emptyList()
    }

    actual suspend fun insertMovies(
        movies: List<MoviePojo>
    ) {}

    actual suspend fun insertMovie(
        movie: MoviePojo
    ) {}

    actual suspend fun removeMovies(
        pagingKey: PagingKey
    ) {}

    actual suspend fun removeMovie(
        pagingKey: PagingKey,
        movieId: MovieId
    ) {}

    actual suspend fun movieById(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo? {
        return null
    }

    actual suspend fun maxPosition(
        pagingKey: PagingKey
    ): Int? {
        return null
    }

    actual suspend fun isEmpty(
        pagingKey: PagingKey
    ): Boolean {
        return true
    }

    actual suspend fun updateMovieColors(
        movieId: MovieId,
        containerColor: Int,
        onContainerColor: Int
    ) {}
}