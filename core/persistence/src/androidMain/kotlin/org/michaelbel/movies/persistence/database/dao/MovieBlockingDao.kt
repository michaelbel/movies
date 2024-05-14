@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

@Dao
internal actual interface MovieBlockingDao {

    @Transaction
    @Query("SELECT * FROM movies WHERE movieList = :pagingKey ORDER BY position ASC")
    fun pagingSource(pagingKey: PagingKey): PagingSource<Int, MoviePojo>
}