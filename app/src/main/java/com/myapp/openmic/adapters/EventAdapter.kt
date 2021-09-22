package com.myapp.openmic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.databinding.EventCardBinding
import com.myapp.openmic.modalclass.Event
import com.squareup.picasso.Picasso

class EventAdapter(private var onEventClick: OnEventCardClick) :
  RecyclerView.Adapter<EventAdapter.ViewHolder>() {
  private var eventList: ArrayList<Event> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = EventCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view, onEventClick)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.populate(position, eventList)

  }

  override fun getItemCount(): Int {
    return eventList.size
  }

  class ViewHolder(binding: EventCardBinding, onEventClick: OnEventCardClick) :
    RecyclerView.ViewHolder(binding.root) {
    private val binding: EventCardBinding

    init {
      this.binding = binding
      binding.mcvEventCard.setOnClickListener {
        val position = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
          onEventClick.showFullInformation(position)
        }
      }

    }

    fun populate(position: Int, eventList: ArrayList<Event>) {
      binding.tvDescription.text = eventList[position].shortDescription
      binding.tvName.text = eventList[position].eventName

      Picasso.get().load(eventList.get(position).eventImageUrl)
        .fit()
        .into(binding.ivEventImage)
    }
  }

  fun setEventList(event: ArrayList<Event>) {
    this.eventList = event
    notifyDataSetChanged()
  }

  interface OnEventCardClick {
    fun showFullInformation(position: Int)
  }

}