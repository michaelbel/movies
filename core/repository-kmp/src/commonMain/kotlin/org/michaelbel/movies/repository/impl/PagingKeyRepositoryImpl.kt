package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.persistence.database.PagingKeyPersistence
import org.michaelbel.movies.persistence.database.entity.PagingKeyPojo
import org.michaelbel.movies.repository.PagingKeyRepository

internal class PagingKeyRepositoryImpl(
    private val pagingKeyPersistence: PagingKeyPersistence
): PagingKeyRepository {

    override suspend fun page(
        pagingKey: String
    ): Int? {
        return pagingKeyPersistence.page(pagingKey)
    }

    override suspend fun totalPages(
        pagingKey: String
    ): Int? {
        return pagingKeyPersistence.totalPages(pagingKey)
    }

    override suspend fun prevPage(
        pagingKey: String
    ): Int? {
        return null
    }

    override suspend fun removePagingKey(
        pagingKey: String
    ) {
        pagingKeyPersistence.removePagingKey(pagingKey)
    }

    override suspend fun insertPagingKey(
        pagingKey: String,
        page: Int,
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