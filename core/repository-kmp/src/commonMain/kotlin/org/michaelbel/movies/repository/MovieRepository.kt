package org.michaelbel.movies.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

interface MovieRepository {

    fun moviesPagingSource(
        pagingKey: String
    ): PagingSource<Int, MoviePojo>

    fun moviesFlow(
        pagingKey: String,
        limit: Int
    ): Flow<List<MoviePojo>>

    suspend fun moviesResult(
        movieList: String,
        page: Int
    ): Result<MovieResponse>

    suspend fun movie(
        pagingKey: String,
        movieId: Int
    ): MoviePojo

    suspend fun movieDetails(
        pagingKey: String,
        movieId: Int
    ): MoviePojo

    suspend fun moviesWidget(): List<MovieDbMini>

    suspend fun removeMovies(
        pagingKey: String
    )

    suspend fun removeMovie(
        pagingKey: String,
        movieId: Int
    )

    suspend fun insertMovies(
        pagingKey: String,
        page: Int,
        movies: List<MovieResponse>
    )

    suspend fun insertMovie(
        pagingKey: String,
        movie: MoviePojo
    )

    suspend fun updateMovieColors(
        movieId: Int,
        containerColor: Int,
        onContainerColor: Int
    )
}