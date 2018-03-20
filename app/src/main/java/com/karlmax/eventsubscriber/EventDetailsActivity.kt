package com.karlmax.eventsubscriber

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.karlmax.eventsubscriber.entities.Event
import kotlinx.android.synthetic.main.activity_event_details.*

class EventDetailsActivity : AppCompatActivity() {

    companion object {

        const val EVENT_ID_SER = "EVENT_ID_SER"
    }

    private var eventId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        eventId = savedInstanceState?.getLong(EVENT_ID_SER) ?: intent.getLongExtra(EVENT_ID_SER, -1)
        ModelProvider.provideEventForId(eventId) { it?.let { updateEvent(it) } }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putLong(EVENT_ID_SER, eventId)
        super.onSaveInstanceState(outState)
    }

    private fun updateEvent(event: Event) {
        name.text = event.name
        startTime.text = event.getStartTimeFormatted(this)
        endTime.text = event.getEndTimeFormatted(this)
        address.text = event.place.getAddressFormatted(this)
    }
}