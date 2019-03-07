package org.michaelbel.moviemade.presentation.features.settings

import android.content.Context
import android.preference.PreferenceManager
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_ADULT

object AdultUtil {

    fun includeAdult(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(KEY_ADULT, true)
    }
}