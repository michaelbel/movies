package org.michaelbel.movies.settings.gender

import org.michaelbel.movies.common.SealedString

interface GrammaticalGenderManager {

    val grammaticalGender: SealedString

    fun setGrammaticalGender(grammaticalGender: Int)
}