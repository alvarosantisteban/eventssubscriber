package com.karlmax.eventsubscriber.entities

import android.content.Context
import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import com.karlmax.eventsubscriber.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * A Facebook Event.
 */
data class Event(val id: Long,
                 val name: String,
                 val description: String,
                 @SerializedName("start_time") val startTime: String?,
                 @SerializedName("end_time") val endTime: String?,
                 val place: EventPlace) {

    fun getStartTimeFormatted(context: Context) =
            context.getString(R.string.event_start_date, reformatDate(startTime))!!

    fun getEndTimeFormatted(context: Context) =
            context.getString(R.string.event_end_date, reformatDate(endTime))!!

    private fun reformatDate(time: String?): String {
        return time?.let {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = df.parse(it).time
            DateFormat.format("dd.MM.yyyy hh:mm", cal).toString()
        } ?: ""
    }

    public fun getDateAsCalendar(time: String?): Calendar? {
        return time?.let {
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = df.parse(it).time
            DateFormat.format("dd.MM.yyyy hh:mm", cal)
            cal
        }
    }
}