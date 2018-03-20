package com.karlmax.eventsubscriber.entities

import android.content.Context
import com.karlmax.eventsubscriber.R

data class EventPlace(val id: Long,
                      val name: String,
                      val country: String,
                      val city: String,
                      val street: String,
                      val zip: String,
                      val latitude: Double,
                      val longitude: Double) {

    fun getAddressFormatted(context: Context) =
            context.getString(R.string.event_address, name, street, zip, city, country)!!
}