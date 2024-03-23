@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.common.gender

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.gender.exceptions.InvalidGenderException

actual sealed class GrammaticalGender(
    val value: Int
): SealedString {

    data object NotSpecified: GrammaticalGender(0)

    data object Neutral: GrammaticalGender(1)

    data object Feminine: GrammaticalGender(2)

    data object Masculine: GrammaticalGender(3)

    companion object {
        val VALUES = listOf(
            NotSpecified,
            Neutral,
            Feminine,
            Masculine
        )

        fun transform(gender: Int): GrammaticalGender {
            return when (gender) {
                0 -> NotSpecified
                1 -> Neutral
                2 -> Feminine
                3 -> Masculine
                else -> throw InvalidGenderException
            }
        }
    }
}