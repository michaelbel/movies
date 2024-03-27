package org.michaelbel.movies.persistence.database.entity

data class PagingKeyPojo(
    val pagingKey: String,
    val page: Int?,
    val totalPages: Int?
)