package org.michaelbel.movies.interactor

import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.persistence.database.typealiases.Query

interface MovieInteractor {

    fun moviesPagingData(
        movieList: MovieList
    ): Flow<PagingData<MoviePojo>>

    fun moviesPagingData(
        searchQuery: Query
    ): Flow<PagingData<MoviePojo>>

    fun moviesPagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo>

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