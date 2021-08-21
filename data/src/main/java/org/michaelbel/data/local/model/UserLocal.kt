package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "users", primaryKeys = ["user_id"])
data class UserLocal(
    @ColumnInfo(name = "user_id", defaultValue = "0") val id: Long = 0L,
    @ColumnInfo(name = "user_name") val name: String? = null
): Serializable