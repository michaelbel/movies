package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.entity.pojo.PagingKeyPojo
import org.michaelbel.movies.persistence.database.ktx.pagingKeyDb
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class PagingKeyPersistence internal constructor(
    private val pagingKeyDao: PagingKeyDao
) {

    suspend fun page(pagingKey: PagingKey): Int? {
        return pagingKeyDao.page(pagingKey)
    }

    suspend fun totalPages(pagingKey: PagingKey): Int? {
        return pagingKeyDao.totalPages(pagingKey)
    }

    suspend fun removePagingKey(pagingKey: PagingKey) {
        pagingKeyDao.removePagingKey(pagingKey)
    }

    suspend fun insertPagingKey(pagingKeyPojo: PagingKeyPojo) {
        pagingKeyDao.insertPagingKey(pagingKeyPojo.pagingKeyDb)
    }
}