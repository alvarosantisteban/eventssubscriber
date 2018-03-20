package com.karlmax.eventsubscriber

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.content_event_list.*

class EventListActivity : AppCompatActivity() {

    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // FIXME Substitute this snackbar for the dialog where the event's organizer's name
            // will be given

            Snackbar.make(view, "Please provide the Fb name of the event's organizer", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

        }

        eventListSwypeToRefresh.setOnRefreshListener { updateEventList() }

        eventList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        eventListAdapter = EventListAdapter()
        eventList.adapter = eventListAdapter
        updateEventList()
    }

    private fun updateEventList() {
        ModelProvider.provideEvents {
            eventListAdapter.items = it
            eventListSwypeToRefresh.isRefreshing = false
        }
    }
}
