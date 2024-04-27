@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database

import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

expect class PagingKeyPersistence {

    suspend fun page(
        pagingKey: PagingKey
    ): Int?

    suspend fun totalPages(
        pagingKey: PagingKey
    ): Int?

    suspend fun removePagingKey(
        pagingKey: PagingKey
    )

    suspend fun insertPagingKey(
        pagingKeyPojo: PagingKeyPojo
    )
}