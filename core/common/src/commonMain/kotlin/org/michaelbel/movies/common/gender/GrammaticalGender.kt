package org.michaelbel.movies.common.gender

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.exceptions.InvalidGenderException

sealed interface GrammaticalGender: SealedString {

    data class NotSpecified(
        val value: Int = 0
    ): GrammaticalGender

    data class Neutral(
        val value: Int = 1
    ): GrammaticalGender

    data class Feminine(
        val value: Int = 2
    ): GrammaticalGender

    data class Masculine(
        val value: Int = 3
    ): GrammaticalGender

    companion object {
        val VALUES = listOf(
            NotSpecified(),
            Neutral(),
            Feminine(),
            Masculine()
        )

        fun transform(gender: Int): GrammaticalGender {
            return when (gender) {
                0 -> NotSpecified()
                1 -> Neutral()
                2 -> Feminine()
                3 -> Masculine()
                else -> throw InvalidGenderException
            }
        }

        fun value(gender: SealedString): Int {
            return when (gender) {
                is NotSpecified -> NotSpecified().value
                is Neutral -> Neutral().value
                is Feminine -> Feminine().value
                is Masculine -> Masculine().value
                else -> throw Exception()
            }
        }
    }
}