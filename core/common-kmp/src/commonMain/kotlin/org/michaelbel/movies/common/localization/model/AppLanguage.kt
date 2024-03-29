package org.michaelbel.movies.common.localization.model

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.localization.exceptions.InvalidLocaleException

sealed interface AppLanguage: SealedString {

    data class English(
        val code: String = "en"
    ): AppLanguage

    data class Russian(
        val code: String = "ru"
    ): AppLanguage

    companion object {
        val VALUES = listOf(
            English(),
            Russian()
        )

        fun transform(code: String): AppLanguage {
            return when (code) {
                "en" -> English()
                "ru" -> Russian()
                else -> throw InvalidLocaleException
            }
        }

        fun code(language: AppLanguage): String {
            return when (language) {
                is English -> English().code
                is Russian -> Russian().code
            }
        }
    }
}