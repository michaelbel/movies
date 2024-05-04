package org.michaelbel.movies.persistence.database.entity.pojo

import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

data class PagingKeyPojo(
    val pagingKey: PagingKey,
    val page: Page?,
    val totalPages: Int?
)