package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import org.jetbrains.annotations.NotNull

@Entity(tableName = "suggestions", primaryKeys = ["title"])
data class SuggestionDb(
    @NotNull val title: String
)