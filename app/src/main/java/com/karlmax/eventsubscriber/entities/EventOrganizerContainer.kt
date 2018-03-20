package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

/**
 * A simple container of EventOrganizer.
 */
data class EventOrganizerContainer(@SerializedName("data") val eventOrganizer: List<EventOrganizer>)