package com.karlmax.eventsubscriber

import android.support.v7.widget.RecyclerView
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
                startTime.text = event.getStartTimeFormatted(context)
                endTime.text = event.getEndTimeFormatted(context)
                address.text = event.place.getAddressFormatted(context)
            }
        }
    }
}