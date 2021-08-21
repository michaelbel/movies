package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "reviews", primaryKeys = ["review_id"])
data class ReviewLocal(
    @ColumnInfo(name = "review_id") val id: String = "",
    @ColumnInfo(name = "review_author") val author: String? = null
): Serializable