package com.karlmax.eventsubscriber

import com.karlmax.eventsubscriber.entities.Event
import com.karlmax.eventsubscriber.entities.EventPlace

object ModelProvider {

    fun provideEvents(listener: (ArrayList<Event>) -> Unit) {
        val place = EventPlace(0, "irgendwo in Berlin", "Deutschland", "Berlin", "fooboo street 1337", "1337", 0.0, 0.0)
        val events = arrayListOf(Event(0, "just a test", "", 0, 0, place),
                Event(0, "just another test", "", 0, 0, place))
        listener(events)
    }
}