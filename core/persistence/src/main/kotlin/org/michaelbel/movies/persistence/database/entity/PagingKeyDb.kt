package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity(tableName = "pagingkeys", primaryKeys = ["movieList"])
data class PagingKeyDb(
    @NotNull val movieList: String,
    @Nullable val page: Int? = null
)