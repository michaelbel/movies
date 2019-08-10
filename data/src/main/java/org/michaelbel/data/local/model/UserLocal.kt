package org.michaelbel.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "users")
data class UserLocal(
        @ColumnInfo(name = "user_id", defaultValue = "0") @PrimaryKey val id: Long = 0L,
        @ColumnInfo(name = "user_name") val name: String? = null
): Serializable