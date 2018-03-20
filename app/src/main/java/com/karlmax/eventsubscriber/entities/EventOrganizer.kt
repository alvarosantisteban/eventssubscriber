package com.karlmax.eventsubscriber.entities

data class EventOrganizer(val id: Long,
                          val name: String,
                          val address: String,
                          val events: List<Event>)