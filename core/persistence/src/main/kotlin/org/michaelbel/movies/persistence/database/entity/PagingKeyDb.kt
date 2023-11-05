package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(tableName = "pagingkeys", primaryKeys = ["movieList"])
data class PagingKeyDb(
    @NotNull val movieList: String,
    val page: Int? = null
)