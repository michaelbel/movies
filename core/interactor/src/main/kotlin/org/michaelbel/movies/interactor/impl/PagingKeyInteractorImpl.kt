package org.michaelbel.movies.interactor.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.PagingKeyInteractor
import org.michaelbel.movies.repository.PagingKeyRepository

@Singleton
internal class PagingKeyInteractorImpl @Inject constructor(
    private val dispatchers: MoviesDispatchers,
    private val pagingKeyRepository: PagingKeyRepository
): PagingKeyInteractor {

    override suspend fun page(pagingKey: String): Int? {
        return withContext(dispatchers.io) {
            pagingKeyRepository.page(pagingKey)
        }
    }

    override suspend fun removePagingKey(pagingKey: String) {
        return withContext(dispatchers.io) {
            pagingKeyRepository.removePagingKey(pagingKey)
        }
    }

    override suspend fun insertPagingKey(pagingKey: String, page: Int) {
        return withContext(dispatchers.io) {
            pagingKeyRepository.insertPagingKey(pagingKey, page)
        }
    }
}