package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity

@Entity(tableName = "suggestions", primaryKeys = ["title"])
data class SuggestionDb(
    val title: String
)