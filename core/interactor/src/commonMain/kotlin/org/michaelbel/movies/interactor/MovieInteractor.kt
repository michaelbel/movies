package org.michaelbel.movies.interactor

import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

interface MovieInteractor {

    fun moviesPagingData(
        movieList: MovieList
    ): Flow<PagingData<MoviePojo>>

    fun moviesPagingData(
        searchQuery: String
    ): Flow<PagingData<MoviePojo>>

    fun moviesPagingSource(
        pagingKey: String
    ): PagingSource<Int, MoviePojo>

    fun moviesFlow(
        pagingKey: String,
        limit: Int
    ): Flow<List<MoviePojo>>

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