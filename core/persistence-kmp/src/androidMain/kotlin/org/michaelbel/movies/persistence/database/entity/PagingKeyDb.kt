@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity(tableName = "pagingkeys", primaryKeys = ["pagingKey"])
data class PagingKeyDb(
    @NotNull val pagingKey: String,
    @Nullable val page: Int?,
    @Nullable val totalPages: Int?
)