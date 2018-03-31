package com.karlmax.eventsubscriber.entities

import android.content.Context
import com.karlmax.eventsubscriber.R

/**
 * The basic information of a spot where an [Event] takes place.
 */
data class EventPlace(val id: Long,
                      val name: String,
                      val location: EventLocation) {

    fun getAddressFormatted(context: Context) =
            context.getString(R.string.event_address, name, location.street, location.zip, location.city, location.country)!!
}