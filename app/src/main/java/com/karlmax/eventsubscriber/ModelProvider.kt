package com.karlmax.eventsubscriber

import com.karlmax.eventsubscriber.api.FacebookApiImplementation
import com.karlmax.eventsubscriber.entities.Event
import com.karlmax.eventsubscriber.entities.EventOrganizer
import java.util.*

/**
 * A utility singleton that provides Models for a scope.
 */
object ModelProvider {

    private val cachedEvents = ArrayList<Event>()
    private val cachedOrganizers = ArrayList<EventOrganizer>()

    fun provideEvents(organizersName : String, listener: (ArrayList<Event>) -> Unit) {
        FacebookApiImplementation.getEventsFromOrganizer(organizersName) {
            cachedEvents.clear()
            cachedEvents.addAll(it)
            listener(filterPastEvents(it) as ArrayList<Event>)
        }
    }

    fun provideEventForId(eventId: Long, listener: (Event?) -> Unit) = listener(cachedEvents.find { it.id == eventId })

    /**
     * Filters the events passed per parameter so only those that are actual (meaning, later than
     * the current time) remain.
     */
    private fun filterPastEvents(events : ArrayList<Event>) : List<Event> {
        return events.filter {
            val eventsEndTime = it.getDateAsCalendar(it.endTime)?.time
            val currentDate =  Calendar.getInstance().time

            eventsEndTime?.after(currentDate) ?: false
        }
    }

    fun provideOrganizeBasicInfo(organizersName: String, listener: (EventOrganizer) -> Unit) {
        FacebookApiImplementation.getBasicInfoFromOrganizer(organizersName) {
            cachedOrganizers.clear()
            cachedOrganizers.add(it)
            listener(it)
        }
    }
}