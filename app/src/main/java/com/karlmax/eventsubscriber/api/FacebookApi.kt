package com.karlmax.eventsubscriber.api

import com.karlmax.eventsubscriber.entities.EventOrganizerContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface to communicate with Facebook's Graph API using Retrofit.
 */
interface FacebookApi {

    companion object {
        const val FACEBOOK_GRAPH_BASE_URL = "https://graph.facebook.com/v2.12/"
    }

    @GET("search")
    fun getOrganizersId(@Query("type") type: String,
                        @Query("q") organizersName: String,
                        @Query("fields") fields: String,
                        @Query("access_token") apiaccessToken: String): Call<EventOrganizerContainer>
}