package com.karlmax.eventsubscriber.entities

import android.content.Context
import android.text.format.DateFormat
import com.karlmax.eventsubscriber.R
import java.util.*

data class Event(val id: Long,
                 val name: String,
                 val description: String,
                 val startTime: Long,
                 val endTime: Long,
                 val place: EventPlace) {

    fun getStartTimeFormatted(context: Context) =
            context.getString(R.string.event_start_date, getDate(startTime))!!

    fun getEndTimeFormatted(context: Context) =
            context.getString(R.string.event_end_date, getDate(endTime))!!

    private fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.format("dd-MM-yyyy hh:mm", cal).toString()
    }
}