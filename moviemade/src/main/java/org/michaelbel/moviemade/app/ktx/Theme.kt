package org.michaelbel.moviemade.app.ktx

import android.content.Context
import org.michaelbel.moviemade.R

val Context.isDarkTheme: Boolean
    get() = resources.getBoolean(R.bool.isDarkTheme)