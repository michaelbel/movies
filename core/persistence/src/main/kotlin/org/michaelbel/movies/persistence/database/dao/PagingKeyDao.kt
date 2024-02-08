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
interface PagingKeyDao {

    @Transaction
    @Query("SELECT * FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun pagingKey(pagingKey: String): PagingKeyDb?

    @Transaction
    @Query("DELETE FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun removePagingKey(pagingKey: String)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagingKey(pagingKey: PagingKeyDb)
}