package com.karlmax.eventsubscriber

import com.karlmax.eventsubscriber.entities.Event
import com.karlmax.eventsubscriber.entities.EventPlace

object ModelProvider {

    private val cachedEvents = ArrayList<Event>()

    init {
        val place = EventPlace(0, "Krasser Schuppen", "Deutschland", "Berlin", "Anderstraße 5", "1337", 0.0, 0.0)
        cachedEvents.add(Event(1, "just a test", "", 0, 0, place))
        cachedEvents.add(Event(2, "just another test", "", 0, 0, place))
    }

    fun provideEvents(listener: (ArrayList<Event>) -> Unit) = listener(cachedEvents)

    fun provideEventForId(eventId: Long, listener: (Event?) -> Unit) = listener(cachedEvents.find { it.id == eventId })
}