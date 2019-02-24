package org.michaelbel.moviemade.core.utils

import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.App
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun formatRuntime(runtime: Int): String {
        val patternMin = "mm"
        val patternHour = "H:mm"

        val formatMin = SimpleDateFormat(patternMin, Locale.US)
        val formatHour = SimpleDateFormat(patternHour, Locale.US)

        var date: Date? = null

        try {
            date = formatMin.parse(runtime.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return App.appContext.getString(R.string.runtime, formatHour.format(date), runtime)
    }

    fun formatReleaseDate(releaseDate: String?): String {
        if (releaseDate == null || releaseDate.isEmpty()) {
            return ""
        }

        val pattern = "yyyy-MM-dd"
        val newPattern = "d MMM yyyy"

        val format = SimpleDateFormat(pattern, Locale.getDefault())
        val newFormat = SimpleDateFormat(newPattern, Locale.US)

        var date: Date? = null
        try {
            date = format.parse(releaseDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return newFormat.format(date)
    }
}