package org.michaelbel.movies.persistence.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

/**
 * The Data Access Object for the [PagingKeyDb] class.
 */
@Dao
interface PagingKeyDao {

    @Query("SELECT page FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun page(pagingKey: PagingKey): Int?

    @Query("SELECT totalPages FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun totalPages(pagingKey: PagingKey): Int?

    @Query("DELETE FROM pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun removePagingKey(pagingKey: PagingKey)

    @Upsert
    suspend fun upsertPagingKey(pagingKey: PagingKeyDb)
}
