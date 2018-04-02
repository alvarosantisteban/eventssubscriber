package com.karlmax.eventsubscriber

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.karlmax.eventsubscriber.entities.EventOrganizer
import kotlinx.android.synthetic.main.item_organizers_list.view.*
import java.util.*

/**
 * The adapter for the list of organizers.
 */
class OrganizersListAdapter () : RecyclerView.Adapter<OrganizersListAdapter.OrganizersListViewHolder>() {

    var items = ArrayList<EventOrganizer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            OrganizersListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_organizers_list, parent,
                    false) as ViewGroup)

    override fun onBindViewHolder(holder: OrganizersListViewHolder, position: Int) = holder.bind(items[position])

    class OrganizersListViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView) {
        fun bind(organizer: EventOrganizer) {
            itemView.apply {
                name.text = organizer.name
                description.text = organizer.description
                Glide.with(this).load(organizer.imageCover.imageUrl).into(coverImage)
            }
        }
    }
}