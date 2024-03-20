@file:Suppress(
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_SUPERTYPES_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING"
)

package org.michaelbel.movies.common.localization.model

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.localization.exceptions.InvalidLocaleException

actual sealed class AppLanguage(
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