@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo

actual class PagingKeyPersistence internal constructor() {

    actual suspend fun page(pagingKey: String): Int? {
        return null
    }

    actual suspend fun totalPages(pagingKey: String): Int? {
        return null
    }

    actual suspend fun removePagingKey(pagingKey: String) {}

    actual suspend fun insertPagingKey(pagingKeyPojo: PagingKeyPojo) {}
}