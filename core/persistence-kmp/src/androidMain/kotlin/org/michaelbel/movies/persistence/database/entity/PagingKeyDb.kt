package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity

@Entity(tableName = "pagingkeys", primaryKeys = ["pagingKey"])
internal data class PagingKeyDb(
    val pagingKey: String,
    val page: Int?,
    val totalPages: Int?
)