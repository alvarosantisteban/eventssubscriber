package com.karlmax.eventsubscriber.entities

/**
 * The info of an organizer of [Event].
 */
data class EventOrganizer(val id: Long,
                          val name: String,
                          val address: String,
                          val events: EventContainer<Event>)