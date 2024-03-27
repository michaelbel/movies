@file:Suppress(
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING"
)

package org.michaelbel.movies.interactor

import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.persistence.database.entity.MoviePojo
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini

actual interface MovieInteractor {

    fun moviesPagingData(movieList: MovieList): Flow<PagingData<MoviePojo>>

    fun moviesPagingData(searchQuery: String): Flow<PagingData<MoviePojo>>

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MoviePojo>

    fun moviesFlow(pagingKey: String, limit: Int): Flow<List<MoviePojo>>

    suspend fun movie(pagingKey: String, movieId: Int): MoviePojo

    suspend fun movieDetails(pagingKey: String, movieId: Int): MoviePojo

    suspend fun moviesWidget(): List<MovieDbMini>

    suspend fun removeMovies(pagingKey: String)

    suspend fun removeMovie(pagingKey: String, movieId: Int)

    suspend fun insertMovie(pagingKey: String, movie: MoviePojo)

    suspend fun updateMovieColors(movieId: Int, containerColor: Int, onContainerColor: Int)
}