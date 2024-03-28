package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
internal data class AccountDb(
    @PrimaryKey val accountId: Int,
    val avatarUrl: String,
    val language: String,
    val country: String,
    val name: String,
    val adult: Boolean,
    val username: String
)