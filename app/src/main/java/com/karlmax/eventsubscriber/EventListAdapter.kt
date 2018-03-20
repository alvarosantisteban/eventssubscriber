package com.karlmax.eventsubscriber

import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import com.karlmax.eventsubscriber.EventListAdapter.EventListViewHolder
import com.karlmax.eventsubscriber.entities.Event
import kotlinx.android.synthetic.main.item_event_list.view.*
import java.util.*

class EventListAdapter : RecyclerView.Adapter<EventListViewHolder>() {

    var items = ArrayList<Event>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event_list, parent, false) as ViewGroup)

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) = holder.bind(items[position]) as Unit

    class EventListViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event) {
            itemView.apply {
                name.text = event.name
                startTime.text = context.getString(R.string.event_start_date, getDate(event.startTime))
                endTime.text = context.getString(R.string.event_end_date, getDate(event.endTime))
                address.text = context.getString(R.string.event_address, event.place.name, event.place.street,
                        event.place.zip, event.place.city, event.place.country)
            }
        }

        private fun getDate(time: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = time
            return DateFormat.format("dd-MM-yyyy hh:mm", cal).toString()
        }
    }
}