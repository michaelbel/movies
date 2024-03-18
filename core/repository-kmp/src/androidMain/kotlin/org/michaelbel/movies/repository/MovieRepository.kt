@file:Suppress(
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)

package org.michaelbel.movies.repository

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

actual interface MovieRepository {

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb>

    fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MovieDb>>

    suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse>

    suspend fun movie(pagingKey: String, movieId: Int): MovieDb

    suspend fun movieDetails(pagingKey: String, movieId: Int): MovieDb

    suspend fun moviesWidget(): List<MovieDbMini>

    suspend fun removeMovies(pagingKey: String)

    suspend fun removeMovie(pagingKey: String, movieId: Int)

    suspend fun insertMovies(pagingKey: String, page: Int, movies: List<MovieResponse>)

    suspend fun insertMovie(pagingKey: String, movie: MovieDb)

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)
}