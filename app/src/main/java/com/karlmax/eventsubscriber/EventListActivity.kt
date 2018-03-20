package com.karlmax.eventsubscriber

import android.os.Bundle
import android.support.v7.app.AlertDialog
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
            val builder = AlertDialog.Builder(this)

            builder.setView(layoutInflater.inflate(R.layout.subscribe_dialog, eventListSwypeToRefresh, false))
                    .setPositiveButton(R.string.organizer_subscriber_subscribe_button, { dialog, id ->
                        // TODO Search in Fb API if there is an ID for the provided string. If yes, add the ID as Organizer, If no, display error message
                    }).show()
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
