package com.karlmax.eventsubscriber

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.GsonBuilder
import com.karlmax.eventsubscriber.api.FacebookAPI
import com.karlmax.eventsubscriber.entities.EventOrganizerContainer
import kotlinx.android.synthetic.main.activity_event_list.*
import kotlinx.android.synthetic.main.content_event_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EventListActivity : AppCompatActivity(), Callback<EventOrganizerContainer> {

    companion object {
        @JvmField val TAG: String = EventListActivity::class.java.simpleName
    }

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
        eventListAdapter = EventListAdapter { startActivity(Intent(this, EventDetailsActivity::class.java)
                .putExtra(EventDetailsActivity.EVENT_ID_SER, it.id)) }
        eventList.adapter = eventListAdapter
        eventList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        updateEventList()

        logOrganizersId()
    }

    private fun updateEventList() {
        ModelProvider.provideEvents {
            eventListAdapter.items = it
            eventListSwypeToRefresh.isRefreshing = false
        }
    }

    fun logOrganizersId() {

        val retrofit = Retrofit.Builder()
                .baseUrl(FacebookAPI.FACEBOOK_GRAPH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        val facebookGraphAPI = retrofit.create(FacebookAPI::class.java)

        val call: Call<EventOrganizerContainer> = facebookGraphAPI.getOrganizersId(
                type = "place",
                organizersName = "ceremonies.berlin", // FIXME Substitute this hardcoded string for the one obtained from dialog
                fields = "name,events",
                apiaccessToken = BuildConfig.FacebookDevToken)
        call.enqueue(this)
    }

    override fun onResponse(call: Call<EventOrganizerContainer>, response: Response<EventOrganizerContainer>) {
        if (response.isSuccessful()) {
            val data = response.body()
            if (data != null) {
                Log.d(TAG, "The organizer: " +data.eventOrganizer.get(0).name +" has the id: " +data.eventOrganizer.get(0).id)
                Log.d(TAG, "The first event has the name: " +data.eventOrganizer.get(0).eventsContainer.events.get(0).name)
            } else {
                Log.e(TAG, "The organizer is null")
            }
        } else {
            Log.e(TAG, "onResponse: " +response.code() + " |" +response.raw().request().url())
        }
    }

    override fun onFailure(call: Call<EventOrganizerContainer>, t: Throwable) {
        Log.e(TAG, "onFailure: " + t.toString())
    }
}
