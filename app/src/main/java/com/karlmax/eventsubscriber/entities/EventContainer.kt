package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

/**
 * A generic container for any list of items which are capped in an extra variable.
 */
data class EventContainer<T>(val data: List<T>)