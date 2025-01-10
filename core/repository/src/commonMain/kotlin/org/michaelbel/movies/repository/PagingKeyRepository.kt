package org.michaelbel.movies.repository

import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

interface PagingKeyRepository {

    suspend fun page(
        pagingKey: PagingKey
    ): Int?

    suspend fun totalPages(
        pagingKey: PagingKey
    ): Int?

    suspend fun prevPage(
        pagingKey: PagingKey
    ): Int?

    suspend fun removePagingKey(
        pagingKey: PagingKey
    )

    suspend fun insertPagingKey(
        pagingKey: PagingKey,
        page: Page,
        totalPages: Int
    )
}