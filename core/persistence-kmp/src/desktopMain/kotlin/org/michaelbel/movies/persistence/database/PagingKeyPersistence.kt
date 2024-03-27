@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo

actual class PagingKeyPersistence internal constructor() {

    suspend fun page(pagingKey: String): Int? {
        return null
    }

    suspend fun totalPages(pagingKey: String): Int? {
        return null
    }

    suspend fun removePagingKey(pagingKey: String) {}

    suspend fun insertPagingKey(pagingKeyPojo: PagingKeyPojo) {}
}