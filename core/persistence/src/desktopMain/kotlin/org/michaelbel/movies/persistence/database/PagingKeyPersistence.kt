@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

actual class PagingKeyPersistence internal constructor() {

    actual suspend fun page(
        pagingKey: PagingKey
    ): Int? {
        return null
    }

    actual suspend fun totalPages(
        pagingKey: PagingKey
    ): Int? {
        return null
    }

    actual suspend fun removePagingKey(
        pagingKey: PagingKey
    ) {}

    actual suspend fun insertPagingKey(
        pagingKeyPojo: PagingKeyPojo
    ) {}
}