package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "movies", primaryKeys = ["movie_id"])
data class MovieLocal(
    @ColumnInfo(name = "movie_id") val id: Long = 0L,
    @ColumnInfo(name = "movie_title") val title: String? = null
): Serializable