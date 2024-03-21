package org.michaelbel.movies.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb

/**
 * The Data Access Object for the [PagingKeyDb] class.
 */
@Dao
internal interface PagingKeyDao {

    @Transaction
    @Query("SELECT page FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun page(pagingKey: String): Int?

    @Transaction
    @Query("SELECT totalPages FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun totalPages(pagingKey: String): Int?

    @Transaction
    @Query("DELETE FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun removePagingKey(pagingKey: String)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagingKey(pagingKey: PagingKeyDb)
}