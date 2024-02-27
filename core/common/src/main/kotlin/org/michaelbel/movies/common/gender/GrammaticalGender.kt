package org.michaelbel.movies.common.gender

import android.content.res.Configuration
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.gender.exceptions.InvalidGenderException

sealed class GrammaticalGender(
    val value: Int
): SealedString {

    data object NotSpecified: GrammaticalGender(Configuration.GRAMMATICAL_GENDER_NOT_SPECIFIED)

    data object Neutral: GrammaticalGender(Configuration.GRAMMATICAL_GENDER_NEUTRAL)

    data object Feminine: GrammaticalGender(Configuration.GRAMMATICAL_GENDER_FEMININE)

    data object Masculine: GrammaticalGender(Configuration.GRAMMATICAL_GENDER_MASCULINE)

    companion object {
        val VALUES = listOf(
            NotSpecified,
            Neutral,
            Feminine,
            Masculine
        )

        fun transform(gender: Int): GrammaticalGender {
            return when (gender) {
                Configuration.GRAMMATICAL_GENDER_NOT_SPECIFIED -> NotSpecified
                Configuration.GRAMMATICAL_GENDER_NEUTRAL -> Neutral
                Configuration.GRAMMATICAL_GENDER_FEMININE -> Feminine
                Configuration.GRAMMATICAL_GENDER_MASCULINE -> Masculine
                else -> throw InvalidGenderException
            }
        }
    }
}