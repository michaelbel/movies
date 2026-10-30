package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.entity.TraktPagingKeyDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class TraktPagingKeyPersistence(
    private val traktDatabase: TraktDatabase
) {

    suspend fun page(pagingKey: PagingKey): Int? {
        return traktDatabase.traktPagingKeyDao.page(pagingKey)
    }

    suspend fun totalPages(pagingKey: PagingKey): Int? {
        return traktDatabase.traktPagingKeyDao.totalPages(pagingKey)
    }

    suspend fun removePagingKey(pagingKey: PagingKey) {
        traktDatabase.traktPagingKeyDao.removePagingKey(pagingKey)
    }

    suspend fun upsertPagingKey(pagingKey: TraktPagingKeyDb) {
        traktDatabase.traktPagingKeyDao.upsertPagingKey(pagingKey)
    }
}
