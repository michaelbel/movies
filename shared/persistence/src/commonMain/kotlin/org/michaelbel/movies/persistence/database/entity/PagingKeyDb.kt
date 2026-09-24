package org.michaelbel.movies.persistence.database.entity

import androidx.room3.Entity
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey

@Entity(
    tableName = "pagingkeys",
    primaryKeys = ["pagingKey"]
)
data class PagingKeyDb(
    val pagingKey: PagingKey,
    val page: Page?,
    val totalPages: Int?
)
