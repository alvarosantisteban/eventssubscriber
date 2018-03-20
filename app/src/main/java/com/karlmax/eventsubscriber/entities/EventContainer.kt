package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

/**
 * A simple container of Event.
 */
data class EventContainer(@SerializedName("data") val events: List<Event>)