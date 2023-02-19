package org.michaelbel.movies.domain.data.entity

import androidx.room.Entity

@Entity(tableName = "pagingkeys", primaryKeys = ["movieList"])
data class PagingKeyDb(
    val movieList: String,
    val page: Int? = null
)