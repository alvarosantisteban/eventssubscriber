package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

data class EventOrganizer(@SerializedName("id") val id: Long,
                          @SerializedName("name") val name: String,
                          @SerializedName("address") val address: String,
                          @SerializedName("events")  val eventsContainer: EventContainer)