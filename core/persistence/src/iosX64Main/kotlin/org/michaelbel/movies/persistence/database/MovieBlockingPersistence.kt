@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import androidx.paging.PagingSource
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

actual class MovieBlockingPersistence internal constructor(
    private val moviesDatabase: MoviesDatabase
) {

    fun pagingSource(pagingKey: PagingKey): PagingSource<Int, MoviePojo> {
        TODO("Not Implemented")
    }
}