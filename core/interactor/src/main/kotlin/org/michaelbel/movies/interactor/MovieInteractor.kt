package org.michaelbel.movies.interactor

import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.persistence.database.entity.MovieDb

interface MovieInteractor {

    fun moviesPagingData(movieList: MovieList): Flow<PagingData<MovieDb>>

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb>

    fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>>

    suspend fun movie(pagingKey: String, movieId: Int): MovieDb

    suspend fun movieDetails(movieId: Int): Either<MovieDb>

    suspend fun removeMovies(pagingKey: String)

    suspend fun removeMovie(pagingKey: String, movieId: Int)

    suspend fun insertMovies(pagingKey: String, movies: List<MovieResponse>)

    suspend fun insertMovie(pagingKey: String, movie: MovieDb)
}