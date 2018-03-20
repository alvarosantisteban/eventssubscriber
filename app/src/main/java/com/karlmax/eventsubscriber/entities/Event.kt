package com.karlmax.eventsubscriber.entities

data class Event(val id: Long,
                 val name: String,
                 val description: String,
                 val startTime: Long,
                 val endTime: Long,
                 val place: EventPlace)