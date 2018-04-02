package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

/**
 * The info of an organizer of [Event]s.
 */
data class EventOrganizer(@SerializedName("id") val id: Long,
                          @SerializedName("name") val name: String,
                          @SerializedName("address") val address: String,
                          @SerializedName("description") val description: String,
                          @SerializedName("cover") val imageCover: Cover,
                          @SerializedName("events") val events: EventContainer<Event>)