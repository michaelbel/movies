package org.michaelbel.movies.repository.impl

import javax.inject.Inject
import javax.inject.Singleton
import org.michaelbel.movies.persistence.database.dao.PagingKeyDao
import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import org.michaelbel.movies.repository.PagingKeyRepository

@Singleton
internal class PagingKeyRepositoryImpl @Inject constructor(
    private val pagingKeyDao: PagingKeyDao
): PagingKeyRepository {

    override suspend fun page(pagingKey: String): Int? {
        return pagingKeyDao.page(pagingKey)
    }

    override suspend fun totalPages(pagingKey: String): Int? {
        return pagingKeyDao.totalPages(pagingKey)
    }

    override suspend fun prevPage(pagingKey: String): Int? {
        return null
    }

    override suspend fun removePagingKey(pagingKey: String) {
        pagingKeyDao.removePagingKey(pagingKey)
    }

    override suspend fun insertPagingKey(pagingKey: String, page: Int, totalPages: Int) {
        pagingKeyDao.insertPagingKey(
            PagingKeyDb(
                pagingKey = pagingKey,
                page = page,
                totalPages = totalPages
            )
        )
    }
}