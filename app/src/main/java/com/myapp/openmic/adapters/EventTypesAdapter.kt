package com.myapp.openmic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.databinding.RvEventBinding
import com.myapp.openmic.modalclass.EventTypes

class EventTypesAdapter() :
  RecyclerView.Adapter<EventTypesAdapter.ViewHolder>() {
  private var eventTypeList: ArrayList<EventTypes> = ArrayList()
  var onEventClick: OnEventCardClickInterface ? = null;

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = RvEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view, onEventClick!!)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.populate(position, eventTypeList)

  }

  override fun getItemCount(): Int {
    return eventTypeList.size
  }

  class ViewHolder(private val binding: RvEventBinding, listener: OnEventCardClickInterface) :
    RecyclerView.ViewHolder(binding.root) {

    init {
      binding.mcvAllEvents.setOnClickListener {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
          listener.onMoreClick(position)
        }
      }

    }

    fun populate(position: Int, eventTypeList: ArrayList<EventTypes>) {

      binding.tvEventType.text = eventTypeList.get(position).name

      val eventAdapter = EventAdapter()
      eventAdapter.setEventList(eventTypeList.get(position).eventList)
      binding.rvEvents.adapter = eventAdapter

    }
  }

  fun setEventTypeList(eventType: ArrayList<EventTypes>) {
    this.eventTypeList = eventType
    notifyDataSetChanged()
  }

  fun setEventClick(onEventCardClick: OnEventCardClickInterface) {
    this.onEventClick = onEventCardClick
  }

  interface OnEventCardClickInterface {
    fun onMoreClick(position: Int)
  }

}