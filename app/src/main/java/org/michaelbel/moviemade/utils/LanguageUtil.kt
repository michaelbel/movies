package org.michaelbel.moviemade.utils

object LanguageUtil {

    fun formatLanguage(languageCode: String): String {

        return if (languageCode == "en") {
            "English"
        } else {
            languageCode
        }
    }
}