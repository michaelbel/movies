package org.michaelbel.movies.repository.impl

import org.michaelbel.movies.persistence.database.PagingKeyPersistence
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import org.michaelbel.movies.repository.PagingKeyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PagingKeyRepositoryImpl @Inject constructor(
    private val pagingKeyPersistence: PagingKeyPersistence
): PagingKeyRepository {

    override suspend fun page(pagingKey: String): Int? {
        return pagingKeyPersistence.page(pagingKey)
    }

    override suspend fun totalPages(pagingKey: String): Int? {
        return pagingKeyPersistence.totalPages(pagingKey)
    }

    override suspend fun prevPage(pagingKey: String): Int? {
        return null
    }

    override suspend fun removePagingKey(pagingKey: String) {
        pagingKeyPersistence.removePagingKey(pagingKey)
    }

    override suspend fun insertPagingKey(pagingKey: String, page: Int, totalPages: Int) {
        pagingKeyPersistence.insertPagingKey(
            PagingKeyDb(
                pagingKey = pagingKey,
                page = page,
                totalPages = totalPages
            )
        )
    }
}