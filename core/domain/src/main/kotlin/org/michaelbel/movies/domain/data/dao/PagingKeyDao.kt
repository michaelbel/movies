package org.michaelbel.movies.domain.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.michaelbel.movies.domain.data.entity.PagingKeyDb

/**
 * The Data Access Object for the [PagingKeyDb] class.
 */
@Dao
internal interface PagingKeyDao {

    @Query("SELECT * FROM pagingkeys WHERE movieList = :movieList")
    suspend fun pagingKey(movieList: String): PagingKeyDb?

    @Query("DELETE FROM pagingkeys WHERE movieList = :movieList")
    suspend fun removePagingKey(movieList: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagingKey(pagingKey: PagingKeyDb)
}