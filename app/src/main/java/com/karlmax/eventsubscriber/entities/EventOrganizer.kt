package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

data class EventOrganizer(val id: Long,
                          val name: String,
                          val address: String,
                          @SerializedName("events")  val eventsContainer: EventContainer)