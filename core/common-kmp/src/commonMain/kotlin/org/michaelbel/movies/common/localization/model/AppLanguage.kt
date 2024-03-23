@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE"
)

package org.michaelbel.movies.common.localization.model

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.localization.exceptions.InvalidLocaleException

sealed class AppLanguage(
    val code: String
): SealedString {

    data object English: AppLanguage("en")

    data object Russian: AppLanguage("ru")

    companion object {
        val VALUES = listOf(
            English,
            Russian
        )

        fun transform(code: String): AppLanguage {
            return when (code) {
                "en" -> English
                "ru" -> Russian
                else -> throw InvalidLocaleException
            }
        }
    }
}