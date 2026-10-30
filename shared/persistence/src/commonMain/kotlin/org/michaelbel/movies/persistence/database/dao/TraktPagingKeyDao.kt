package org.michaelbel.movies.persistence.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import org.michaelbel.movies.persistence.database.entity.TraktPagingKeyDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

/**
 * The Data Access Object for the [TraktPagingKeyDb] class.
 */
@Dao
interface TraktPagingKeyDao {

    @Transaction
    @Query("SELECT page FROM trakt_pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun page(pagingKey: PagingKey): Int?

    @Transaction
    @Query("SELECT totalPages FROM trakt_pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun totalPages(pagingKey: PagingKey): Int?

    @Transaction
    @Query("DELETE FROM trakt_pagingkeys WHERE pagingKey = :pagingKey")
    suspend fun removePagingKey(pagingKey: PagingKey)

    @Transaction
    @Upsert
    suspend fun upsertPagingKey(pagingKey: TraktPagingKeyDb)
}
