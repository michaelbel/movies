@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo
import org.michaelbel.movies.persistence.database.ktx.pagingKeyDb

actual class PagingKeyPersistence internal constructor(
    private val pagingKeyDao: PagingKeyDao
) {

    actual suspend fun page(pagingKey: String): Int? {
        return pagingKeyDao.page(pagingKey)
    }

    actual suspend fun totalPages(pagingKey: String): Int? {
        return pagingKeyDao.totalPages(pagingKey)
    }

    actual suspend fun removePagingKey(pagingKey: String) {
        pagingKeyDao.removePagingKey(pagingKey)
    }

    actual suspend fun insertPagingKey(pagingKeyPojo: PagingKeyPojo) {
        pagingKeyDao.insertPagingKey(pagingKeyPojo.pagingKeyDb)
    }
}