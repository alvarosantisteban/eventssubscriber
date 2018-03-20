package com.karlmax.eventsubscriber

import com.karlmax.eventsubscriber.api.FacebookApiImplementation
import com.karlmax.eventsubscriber.entities.Event

object ModelProvider {

    private val cachedEvents = ArrayList<Event>()

    fun provideEvents(organizersName : String, listener: (ArrayList<Event>) -> Unit) {
        FacebookApiImplementation.getEventsFromOrganizer(organizersName) {
            cachedEvents.clear()
            cachedEvents.addAll(it)
            listener(it)
        }
    }

    fun provideEventForId(eventId: Long, listener: (Event?) -> Unit) = listener(cachedEvents.find { it.id == eventId })
}