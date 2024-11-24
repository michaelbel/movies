package org.michaelbel.movies.settings.gender.impl

import android.content.Context
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.settings.gender.GrammaticalGenderManager
import org.michaelbel.movies.settings.ktx.currentGrammaticalGender
import org.michaelbel.movies.settings.ktx.supportSetRequestedApplicationGrammaticalGender

class GrammaticalGenderManagerImpl(
    private val context: Context
): GrammaticalGenderManager {

    override val grammaticalGender: SealedString
        get() = context.currentGrammaticalGender

    override fun setGrammaticalGender(grammaticalGender: Int) {
        context.supportSetRequestedApplicationGrammaticalGender(grammaticalGender)
    }
}