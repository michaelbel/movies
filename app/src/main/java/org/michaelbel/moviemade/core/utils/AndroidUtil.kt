package org.michaelbel.moviemade.core.utils

import org.michaelbel.moviemade.core.entity.Country
import org.michaelbel.moviemade.presentation.App

object AndroidUtil {

    fun runOnUIThread(runnable: Runnable, delay: Long) {
        if (delay == 0L) {
            App.appHandler.post(runnable)
        } else {
            App.appHandler.postDelayed(runnable, delay)
        }
    }

    fun cancelRunOnUIThread(runnable: Runnable) {
        App.appHandler.removeCallbacks(runnable)
    }

    fun formatCountries(countries: List<Country>): String {
        if (countries.isEmpty()) {
            return ""
        }

        val text = StringBuilder()
        for (country in countries) {
            text.append(country.name).append(", ")
        }

        text.delete(text.toString().length - 2, text.toString().length)
        return text.toString()
    }
}