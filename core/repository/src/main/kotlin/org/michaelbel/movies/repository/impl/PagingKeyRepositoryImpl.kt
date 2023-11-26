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
        return pagingKeyDao.pagingKey(pagingKey)?.page
    }

    override suspend fun removePagingKey(pagingKey: String) {
        pagingKeyDao.removePagingKey(pagingKey)
    }

    override suspend fun insertPagingKey(pagingKey: String, page: Int) {
        pagingKeyDao.insertPagingKey(
            PagingKeyDb(
                pagingKey = pagingKey,
                page = page
            )
        )
    }
}