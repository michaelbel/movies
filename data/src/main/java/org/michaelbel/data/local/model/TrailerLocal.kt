package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "trailers", primaryKeys = ["trailer_id"])
data class TrailerLocal(
    @ColumnInfo(name = "trailer_id") val id: String = "",
    @ColumnInfo(name = "trailer_title") val title: String? = null
): Serializable