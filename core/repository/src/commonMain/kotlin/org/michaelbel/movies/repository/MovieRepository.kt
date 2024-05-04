package org.michaelbel.movies.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

interface MovieRepository {

    fun moviesPagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo>

    fun moviesFlow(
        pagingKey: PagingKey,
        limit: Limit
    ): Flow<List<MoviePojo>>

    suspend fun moviesResult(
        pagingKey: PagingKey,
        language: String,
        page: Page
    ): Result<MovieResponse>

    suspend fun movie(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo

    suspend fun movieDetails(
        pagingKey: PagingKey,
        language: String,
        movieId: MovieId
    ): MoviePojo

    suspend fun moviesWidget(
        language: String
    ): List<MovieDbMini>

    suspend fun removeMovies(
        pagingKey: PagingKey
    )

    suspend fun removeMovie(
        pagingKey: PagingKey,
        movieId: MovieId
    )

    suspend fun insertMovies(
        pagingKey: PagingKey,
        page: Page,
        movies: List<MovieResponse>
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