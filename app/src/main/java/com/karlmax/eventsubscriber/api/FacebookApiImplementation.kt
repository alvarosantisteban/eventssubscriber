package com.karlmax.eventsubscriber.api

import android.util.Log
import com.google.gson.GsonBuilder
import com.karlmax.eventsubscriber.BuildConfig
import com.karlmax.eventsubscriber.EventListActivity
import com.karlmax.eventsubscriber.entities.Event
import com.karlmax.eventsubscriber.entities.EventOrganizerContainer
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

        val call: Call<EventOrganizerContainer> = facebookGraphAPI.getOrganizersId(
                type = "place",
                organizersName = organizersName,
                fields = "name,events",
                apiaccessToken = BuildConfig.FacebookDevToken)
        call.enqueue(object : Callback<EventOrganizerContainer> {
            override fun onResponse(call: Call<EventOrganizerContainer>?, response: Response<EventOrganizerContainer>?) {
                if (response !=null && response.isSuccessful()) {
                    val data = response.body()
                    if (data != null) {
                        Log.d(EventListActivity.TAG, "The organizer: " + data.eventOrganizer.get(0).name + " has the id: " + data.eventOrganizer.get(0).id)
                        Log.d(EventListActivity.TAG, "The first event has the name: " + data.eventOrganizer.get(0).eventsContainer.events.get(0).name)
                        listener(data.eventOrganizer.get(0).eventsContainer.events as ArrayList<Event>)
                    } else {
                        Log.e(EventListActivity.TAG, "The organizer is null")
                    }
                } else {
                    if(response != null) {
                        Log.e(EventListActivity.TAG, "onResponse: " + response.code() + " |" + response.raw().request().url())
                    }
                }
            }

            override fun onFailure(call: Call<EventOrganizerContainer>?, t: Throwable?) {
                Log.e(EventListActivity.TAG, "onFailure: " + t.toString())
            }

        })
    }
}