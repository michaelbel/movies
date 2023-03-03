package org.michaelbel.movies.domain.interactor.movie

import androidx.paging.PagingSource
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.entities.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result

interface MovieInteractor {

    fun moviesPagingSource(movieList: String): PagingSource<Int, MovieDb>

    suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse>

    suspend fun movieDetails(movieId: Int): Either<MovieDb>

    suspend fun removeAllMovies(movieList: String)

    suspend fun insertAllMovies(movieList: String, movies: List<MovieResponse>)

    suspend fun page(movieList: String): Int?

    suspend fun removePagingKey(movieList: String)

    suspend fun insertPagingKey(movieList: String, page: Int)
}