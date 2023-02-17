package org.michaelbel.movies.domain.data.converter

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = value }
    }
}