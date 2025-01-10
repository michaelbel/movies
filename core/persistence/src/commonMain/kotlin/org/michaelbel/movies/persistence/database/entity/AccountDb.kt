package org.michaelbel.movies.persistence.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.michaelbel.movies.persistence.database.typealiases.AccountId

@Entity(tableName = "accounts")
data class AccountDb(
    @PrimaryKey val accountId: AccountId,
    val avatarUrl: String,
    val language: String,
    val country: String,
    val name: String,
    val adult: Boolean,
    val username: String
)