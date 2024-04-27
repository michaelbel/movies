package org.michaelbel.movies.persistence.database.entity

import org.michaelbel.movies.persistence.database.typealiases.AccountId

data class AccountPojo(
    val accountId: AccountId,
    val avatarUrl: String,
    val language: String,
    val country: String,
    val name: String,
    val adult: Boolean,
    val username: String
) {
    companion object {
        val Empty = AccountPojo(
            accountId = 0,
            avatarUrl = "",
            language = "",
            country = "",
            name = "",
            adult = false,
            username = ""
        )
    }
}