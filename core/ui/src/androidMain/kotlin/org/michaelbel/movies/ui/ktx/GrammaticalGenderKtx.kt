package org.michaelbel.movies.ui.ktx

import android.app.GrammaticalInflectionManager
import android.content.Context
import android.os.Build
import org.michaelbel.movies.common.gender.GrammaticalGender

val Context.currentGrammaticalGender: GrammaticalGender
    get() {
        return if (Build.VERSION.SDK_INT >= 34) {
            val grammaticalInflectionManager = getSystemService(GrammaticalInflectionManager::class.java)
            val grammaticalGender = grammaticalInflectionManager.applicationGrammaticalGender
            GrammaticalGender.transform(grammaticalGender)
        } else {
            GrammaticalGender.NotSpecified()
        }
    }

fun Context.supportSetRequestedApplicationGrammaticalGender(grammaticalGender: Int) {
    if (Build.VERSION.SDK_INT >= 34) {
        val grammaticalInflectionManager = getSystemService(GrammaticalInflectionManager::class.java)
        grammaticalInflectionManager.setRequestedApplicationGrammaticalGender(grammaticalGender)
    }
}