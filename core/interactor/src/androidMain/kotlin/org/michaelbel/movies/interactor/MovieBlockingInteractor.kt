@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.interactor

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.Query

actual interface MovieBlockingInteractor {

    fun moviesPagingData(
        movieList: MovieList
    ): Flow<PagingData<MoviePojo>>

    fun moviesPagingData(
        searchQuery: Query
    ): Flow<PagingData<MoviePojo>>
}