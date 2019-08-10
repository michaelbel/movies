package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "trailers")
data class TrailerLocal(
        @ColumnInfo(name = "trailer_id") @PrimaryKey val id: String = "",
        @ColumnInfo(name = "trailer_title") val title: String? = null
): Serializable