package org.michaelbel.movies.settings.ktx

import android.app.GrammaticalInflectionManager
import android.content.Context
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.settings.model.isGenderFeatureEnabled

internal val Context.currentGrammaticalGender: GrammaticalGender
    get() {
        return if (isGenderFeatureEnabled) {
            val grammaticalInflectionManager = getSystemService(GrammaticalInflectionManager::class.java)
            val grammaticalGender = grammaticalInflectionManager.applicationGrammaticalGender
            GrammaticalGender.transform(grammaticalGender)
        } else {
            GrammaticalGender.NotSpecified()
        }
    }

internal fun Context.supportSetRequestedApplicationGrammaticalGender(grammaticalGender: Int) {
    if (isGenderFeatureEnabled) {
        val grammaticalInflectionManager = getSystemService(GrammaticalInflectionManager::class.java)
        grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(grammaticalGender)
    }
}