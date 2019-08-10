package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "reviews")
data class ReviewLocal(
        @ColumnInfo(name = "review_id") @PrimaryKey val id: String = "",
        @ColumnInfo(name = "review_author") val author: String? = null
): Serializable