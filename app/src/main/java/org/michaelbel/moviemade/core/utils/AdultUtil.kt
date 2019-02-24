package org.michaelbel.moviemade.core.utils

import android.content.Context
import android.preference.PreferenceManager

object AdultUtil {

    fun includeAdult(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean("key_adult", true)
    }
}