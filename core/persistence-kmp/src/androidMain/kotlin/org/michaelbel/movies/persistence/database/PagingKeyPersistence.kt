@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import javax.inject.Inject

actual class PagingKeyPersistence @Inject internal constructor(
    private val pagingKeyDao: PagingKeyDao
) {

    suspend fun page(pagingKey: String): Int? {
        return pagingKeyDao.page(pagingKey)
    }

    suspend fun totalPages(pagingKey: String): Int? {
        return pagingKeyDao.totalPages(pagingKey)
    }

    suspend fun removePagingKey(pagingKey: String) {
        pagingKeyDao.removePagingKey(pagingKey)
    }

    suspend fun insertPagingKey(pagingKey: PagingKeyDb) {
        pagingKeyDao.insertPagingKey(pagingKey)
    }
}