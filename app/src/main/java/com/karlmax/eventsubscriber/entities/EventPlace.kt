package com.karlmax.eventsubscriber.entities

data class EventPlace(val id: Long,
                      val name: String,
                      val country: String,
                      val city: String,
                      val street: String,
                      val zip: String,
                      val latitude: Double,
                      val longitude: Double)