package org.michaelbel.moviemade.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object AdultUtil {

    fun includeAdult(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_ADULT, true)
    }
}