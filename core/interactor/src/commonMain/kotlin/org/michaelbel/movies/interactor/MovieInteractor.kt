package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

interface MovieInteractor {

    fun moviesFlow(
        pagingKey: PagingKey,
        limit: Limit
    ): Flow<List<MoviePojo>>

    suspend fun movie(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo

    suspend fun movieDetails(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo

    suspend fun moviesWidget(): List<MovieDbMini>

    suspend fun removeMovies(
        pagingKey: PagingKey
    )

    suspend fun removeMovie(
        pagingKey: PagingKey,
        movieId: MovieId
    )

    suspend fun insertMovie(
        pagingKey: PagingKey,
        movie: MoviePojo
    )

    suspend fun updateMovieColors(
        movieId: MovieId,
        containerColor: Int,
        onContainerColor: Int
    )
}