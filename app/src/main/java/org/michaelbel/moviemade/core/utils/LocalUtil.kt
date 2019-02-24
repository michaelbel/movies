package org.michaelbel.moviemade.core.utils

import android.content.Context
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.consts.Langs

object LocalUtil {

    fun getLanguage(context: Context): String {
        val languageCode = context.getString(R.string.language_code)

        return when (languageCode) {
            "en" -> Langs.LANG_EN_US
            "ru" -> Langs.LANG_RU
            else -> Langs.LANG_EN_US
        }
    }
}