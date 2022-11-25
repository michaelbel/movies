package org.michaelbel.movies.ui.language.model

import org.michaelbel.movies.ui.language.exceptions.InvalidLocaleException

sealed class AppLanguage(val code: String) {
    object English: AppLanguage("en") {
        override fun toString(): String = "English"
    }

    object Russian: AppLanguage("ru") {
        override fun toString(): String = "Russian"
    }

    companion object {
        fun transform(code: String): AppLanguage {
            return when (code) {
                "en" -> English
                "ru" -> Russian
                else -> throw InvalidLocaleException
            }
        }
    }
}