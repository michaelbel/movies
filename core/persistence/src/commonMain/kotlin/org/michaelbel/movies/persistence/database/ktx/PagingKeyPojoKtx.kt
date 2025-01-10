package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.PagingKeyDb
import org.michaelbel.movies.persistence.database.entity.pojo.PagingKeyPojo

internal val PagingKeyPojo.pagingKeyDb: PagingKeyDb
    get() = PagingKeyDb(
        pagingKey = pagingKey,
        page = page,
        totalPages = totalPages
    )