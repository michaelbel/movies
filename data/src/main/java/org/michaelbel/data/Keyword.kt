package org.michaelbel.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "keywords")
data class Keyword(
        @Expose @SerializedName("id") @PrimaryKey val id: Int = 0,
        @Expose @SerializedName("name") val name: String = "",

        val movieId: Int = 0
): Serializable