@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.repository

import androidx.paging.PagingSource
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

actual interface MovieBlockingRepository {

    fun moviesPagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo>
}