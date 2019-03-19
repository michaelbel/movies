package org.michaelbel.moviemade.core.time

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun formatRuntime(runtime: Int): String {
        val formatMin = SimpleDateFormat("mm", Locale.ENGLISH)
        val formatHour = SimpleDateFormat("H:mm", Locale.ENGLISH)

        val date = formatMin.parse(runtime.toString())
        return formatHour.format(date)
    }

    fun formatReleaseDate(releaseDate: String): String {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newFormat = SimpleDateFormat("d MMM yyyy", Locale.ENGLISH)

        val date = oldFormat.parse(releaseDate)
        return newFormat.format(date)
    }
}