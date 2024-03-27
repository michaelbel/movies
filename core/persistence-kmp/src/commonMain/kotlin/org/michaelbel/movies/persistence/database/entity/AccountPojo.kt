package org.michaelbel.movies.persistence.database.entity

data class AccountPojo(
    val accountId: Int,
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