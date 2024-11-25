package org.michaelbel.movies.settings.gender.impl

import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.settings.gender.GrammaticalGenderManager

class GrammaticalGenderManagerImpl: GrammaticalGenderManager {

    override val grammaticalGender: SealedString
        get() = GrammaticalGender.NotSpecified()

    override fun setGrammaticalGender(grammaticalGender: Int) {}
}