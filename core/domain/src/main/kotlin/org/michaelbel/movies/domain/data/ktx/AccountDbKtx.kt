package org.michaelbel.movies.domain.data.ktx

import org.michaelbel.movies.domain.data.entity.AccountDb

private const val ACCOUNT_DEFAULT_LETTER = "A"
private const val SPACE_UNICODE = "\u0020"
private const val LETTERS_LIMIT = 2
private const val FIRST_LETTER_INDEX = 1

val AccountDb.isEmpty: Boolean
    get() = this == AccountDb.Empty

val AccountDb?.orEmpty: AccountDb
    get() = this ?: AccountDb.Empty

val AccountDb.letters: String
    get() = when {
        name.isNotEmpty() -> {
            val words: List<String> = name.split(SPACE_UNICODE)
            val letters: List<String> =  words.subList(0, LETTERS_LIMIT).map { word ->
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