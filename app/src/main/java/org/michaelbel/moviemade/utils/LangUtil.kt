package org.michaelbel.moviemade.utils

import android.content.Context
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.data.constants.LANG_EN_US
import org.michaelbel.moviemade.data.constants.LANG_RU

object LangUtil {

    // FIXME add countries flags.
    fun formatLanguage(languageCode: String): String {

        return if (languageCode == "en") {
            "English"
        } else if (languageCode == "ru") {
            "Russian"
        } else {
            languageCode
        }
    }

    fun getLanguage(context: Context): String {
        val languageCode = context.getString(R.string.language_code)

        return when (languageCode) {
            "en" -> LANG_EN_US
            "ru" -> LANG_RU
            else -> LANG_EN_US
        }
    }
}