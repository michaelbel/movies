package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.persistence.database.entity.AccountDb

private const val ACCOUNT_DEFAULT_LETTER = "A"
private const val SPACE_UNICODE = "\u0020"
private const val LETTERS_LIMIT = 2
private const val FIRST_LETTER_INDEX = 1

actual val AccountDb.isEmpty: Boolean
    get() = this == AccountDb.Empty

actual val AccountDb?.orEmpty: AccountDb
    get() = this ?: AccountDb.Empty

actual val AccountDb.letters: String
    get() = when {
        name.isNotEmpty() -> {
            val words = name.split(SPACE_UNICODE)
            val letters = words.subList(0, LETTERS_LIMIT).map { word ->
                word.substring(0, FIRST_LETTER_INDEX)
            }
            letters.joinToString(
                separator = "",
                limit = LETTERS_LIMIT
            )
        }
        username.isNotEmpty() -> username.substring(0, FIRST_LETTER_INDEX)
        else -> ACCOUNT_DEFAULT_LETTER
    }