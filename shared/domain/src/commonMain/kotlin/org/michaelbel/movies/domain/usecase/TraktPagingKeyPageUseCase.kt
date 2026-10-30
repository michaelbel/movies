@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package org.michaelbel.movies.domain.usecase

import org.michaelbel.movies.common.dispatchers.SharedDispatchers
import org.michaelbel.movies.persistence.database.TraktPagingKeyPersistence
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

class TraktPagingKeyPageUseCase(
    private val traktPagingKeyPersistence: TraktPagingKeyPersistence,
    dispatchers: SharedDispatchers
): UseCase<PagingKey, Int?>(dispatchers.io) {

    override suspend fun execute(pagingKey: PagingKey): Int? {
        return traktPagingKeyPersistence.page(pagingKey)
    }
}
