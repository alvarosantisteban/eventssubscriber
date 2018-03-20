package com.karlmax.eventsubscriber.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.karlmax.eventsubscriber.BuildConfig
import com.karlmax.eventsubscriber.EventListActivity
import com.karlmax.eventsubscriber.entities.Event
import com.karlmax.eventsubscriber.entities.EventContainer
import com.karlmax.eventsubscriber.entities.EventOrganizer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The implementation that handles the communication with Facebook's API and retrieves a list of
 * events for a given organizer's name.
 */
object FacebookApiImplementation {

    fun getEventsFromOrganizer(organizersName: String, listener: (ArrayList<Event>) -> Unit) {

        val retrofit = Retrofit.Builder()
                .baseUrl(FacebookApi.FACEBOOK_GRAPH_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        val facebookGraphAPI = retrofit.create(FacebookApi::class.java)

        val call: Call<EventContainer<EventOrganizer>> = facebookGraphAPI.getOrganizersId(
                type = "place",
                organizersName = organizersName,
                fields = "name,events",
                apiaccessToken = BuildConfig.FacebookDevToken)
        call.enqueue(object : Callback<EventContainer<EventOrganizer>> {
            override fun onResponse(call: Call<EventContainer<EventOrganizer>>?, response: Response<EventContainer<EventOrganizer>>?) {
                if (response !=null && response.isSuccessful()) {
                    val data = response.body()
                    if (data != null) {
                        Log.d(EventListActivity.TAG, "The organizer: " + data.data.get(0).name + " has the id: " + data.data.get(0).id)
                        Log.d(EventListActivity.TAG, "The first event has the name: " + data.data.get(0).events.data.get(0).name)
                        listener(data.data.get(0).events.data as ArrayList<Event>)
                    } else {
                        Log.e(EventListActivity.TAG, "The organizer is null")
                    }
                } else {
                    if(response != null) {
                        Log.e(EventListActivity.TAG, "onResponse: " + response.code() + " |" + response.raw().request().url())
                    }
                }
            }

            override fun onFailure(call: Call<EventContainer<EventOrganizer>>?, t: Throwable?) {
                Log.e(EventListActivity.TAG, "onFailure: " + t.toString())
            }

        })
    }
}