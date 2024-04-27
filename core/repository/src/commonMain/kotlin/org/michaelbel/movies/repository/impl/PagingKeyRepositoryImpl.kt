package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.persistence.database.PagingKeyPersistence
import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.repository.PagingKeyRepository

internal class PagingKeyRepositoryImpl(
    private val pagingKeyPersistence: PagingKeyPersistence
): PagingKeyRepository {

    override suspend fun page(
        pagingKey: PagingKey
    ): Int? {
        return pagingKeyPersistence.page(pagingKey)
    }

    override suspend fun totalPages(
        pagingKey: PagingKey
    ): Int? {
        return pagingKeyPersistence.totalPages(pagingKey)
    }

    override suspend fun prevPage(
        pagingKey: PagingKey
    ): Int? {
        return null
    }

    override suspend fun removePagingKey(
        pagingKey: PagingKey
    ) {
        pagingKeyPersistence.removePagingKey(pagingKey)
    }

    override suspend fun insertPagingKey(
        pagingKey: PagingKey,
        page: Page,
        totalPages: Int
    ) {
        pagingKeyPersistence.insertPagingKey(
            PagingKeyPojo(
                pagingKey = pagingKey,
                page = page,
                totalPages = totalPages
            )
        )
    }
}