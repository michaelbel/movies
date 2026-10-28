package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.entity.pojo.PagingKeyPojo
import org.michaelbel.movies.persistence.database.ktx.pagingKeyDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class PagingKeyPersistence(
    private val moviesDatabase: MoviesDatabase
) {

    suspend fun page(pagingKey: PagingKey): Int? {
        return moviesDatabase.pagingKeyDao.page(pagingKey)
    }

    suspend fun totalPages(pagingKey: PagingKey): Int? {
        return moviesDatabase.pagingKeyDao.totalPages(pagingKey)
    }

    suspend fun removePagingKey(pagingKey: PagingKey) {
        moviesDatabase.pagingKeyDao.removePagingKey(pagingKey)
    }

    suspend fun upsertPagingKey(pagingKeyPojo: PagingKeyPojo) {
        moviesDatabase.pagingKeyDao.upsertPagingKey(pagingKeyPojo.pagingKeyDb)
    }
}
