package org.michaelbel.movies.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb

interface MovieRepository {

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb>

    fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>>

    fun moviePosition(pagingKey: String?, movieId: Int): Flow<Int>

    suspend fun moviesOnPage(pagingKey: String, page: Int): List<MovieDb>

    suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse>

    suspend fun movie(pagingKey: String, movieId: Int): MovieDb

    suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb

    suspend fun removeMovies(pagingKey: String)

    suspend fun removeMovie(pagingKey: String, movieId: Int)

    suspend fun insertMovies(pagingKey: String, page: Int, movies: List<MovieResponse>)

    suspend fun insertMovie(pagingKey: String, movie: MovieDb)

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)
}