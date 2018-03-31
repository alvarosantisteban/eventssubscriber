package com.karlmax.eventsubscriber

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.content_event_list.*

/**
 * Displays a list of the [Event]s organised from the [EventOrganizer] added through the FAB.
 * The events are retrieved from Facebook's Graph API Explorer.
 */
class EventListActivity : AppCompatActivity() {

    companion object {
        @JvmField val TAG: String = EventListActivity::class.java.simpleName
    }

    private lateinit var eventListAdapter: EventListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val dialogView = layoutInflater.inflate(R.layout.subscribe_dialog, eventListSwypeToRefresh, false)
            val builder = AlertDialog.Builder(this)

            builder.setView(dialogView)
                    .setPositiveButton(R.string.organizer_subscriber_subscribe_button, { dialog, id ->
                        // TODO Search in Fb API if there is an ID for the provided string.
                        // If yes, add the ID as Organizer, If no, display error message

                        val editText = dialogView.findViewById<View>(R.id.dialogOrganizer) as EditText
                        val organizersName = editText.text.toString()
                        val persistor = OrganizerKeyPersistor()
                        persistor.persistKey(this, organizersName)
                        updateEventList()
                    }).show()
        }

        eventListSwypeToRefresh.setOnRefreshListener { updateEventList() }

        eventList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        eventListAdapter = EventListAdapter { startActivity(Intent(this, EventDetailsActivity::class.java)
                .putExtra(EventDetailsActivity.EVENT_ID_SER, it.id)) }
        eventList.adapter = eventListAdapter
        eventList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        updateEventList()
    }

    /**
     * Updates the list of events by emptying the list and asking the API for all the current events
     * of the persisted organisers.
     */
    private fun updateEventList() {
        val persistor = OrganizerKeyPersistor()
        Log.d(TAG, "Stored keys: " +persistor.getPersistedKeys(this).joinToString())

        if (!persistor.isEmpty(this)) {
            // Empty the adapter before getting the new events
            eventListAdapter.items.clear()

            val organizers = persistor.getPersistedKeys(this)
            for (organizer: String in organizers) {
                ModelProvider.provideEvents(organizer) {
                    if (eventListAdapter.items.isEmpty()) {
                        eventListAdapter.items = it
                    } else {
                        eventListAdapter.items.addAll(it)
                    }
                    eventListSwypeToRefresh.isRefreshing = false
                    eventListAdapter.notifyDataSetChanged()
                }
            }
        } else {
            Log.d(TAG, "There are no organizers -> The list is empty")
        }
    }
}
