package com.karlmax.eventsubscriber.entities

import android.content.Context
import android.text.format.DateFormat
import com.google.gson.annotations.SerializedName
import com.karlmax.eventsubscriber.R
import java.util.*

data class Event(val id: Long,
                 val name: String,
                 val description: String,
                 @SerializedName("start_time") val startTime: String,
                 @SerializedName("end_time") val endTime: String,
                 val place: EventPlace) {

    fun getStartTimeFormatted(context: Context) =
            context.getString(R.string.event_start_date, startTime)!!

    fun getEndTimeFormatted(context: Context) =
            context.getString(R.string.event_end_date, endTime)!!

    private fun getDate(time: Long): String {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.format("dd-MM-yyyy hh:mm", cal).toString()
    }
}