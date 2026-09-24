package org.michaelbel.movies.persistence.database.entity

import androidx.room3.Entity
import org.michaelbel.movies.persistence.database.typealiases.AccountId

@Entity(
    tableName = "accounts",
    primaryKeys = ["accountId"]
)
data class AccountDb(
    val accountId: AccountId,
    val avatarUrl: String,
    val language: String,
    val country: String,
    val name: String,
    val adult: Boolean,
    val username: String
)
