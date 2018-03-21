package com.karlmax.eventsubscriber

import com.karlmax.eventsubscriber.api.FacebookApiImplementation
import com.karlmax.eventsubscriber.entities.Event
import java.util.*

object ModelProvider {

    private val cachedEvents = ArrayList<Event>()

    fun provideEvents(organizersName : String, listener: (ArrayList<Event>) -> Unit) {
        FacebookApiImplementation.getEventsFromOrganizer(organizersName) {
            cachedEvents.clear()
            cachedEvents.addAll(it)
            listener(filterPastEvents(it) as ArrayList<Event>)
        }
    }

    fun provideEventForId(eventId: Long, listener: (Event?) -> Unit) = listener(cachedEvents.find { it.id == eventId })

    fun filterPastEvents(events : ArrayList<Event>) : List<Event> {
        return events.filter {
            val eventsEndTime = it.getDateAsCalendar(it.endTime)?.time
            val currentDate =  Calendar.getInstance().time

            eventsEndTime?.after(currentDate) ?: false
        }
    }
}