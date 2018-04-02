package com.karlmax.eventsubscriber.entities

import com.google.gson.annotations.SerializedName

/**
 * An image cover.
 */
data class Cover (@SerializedName("source") val imageUrl: String)