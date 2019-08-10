package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "movies")
data class MovieLocal(
        @ColumnInfo(name = "movie_id") @PrimaryKey val id: Long = 0L,
        @ColumnInfo(name = "movie_title") val title: String? = null
): Serializable