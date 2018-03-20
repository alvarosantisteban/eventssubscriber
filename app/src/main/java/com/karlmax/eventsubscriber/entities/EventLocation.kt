package com.karlmax.eventsubscriber.entities

data class EventLocation(val country: String,
                         val city: String,
                         val street: String,
                         val zip: String,
                         val latitude: Double,
                         val longitude: Double)