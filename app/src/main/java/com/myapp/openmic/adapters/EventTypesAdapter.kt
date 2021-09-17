package com.myapp.openmic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.databinding.RvEventBinding
import com.myapp.openmic.modalclass.EventTypes

class EventTypesAdapter : RecyclerView.Adapter<EventTypesAdapter.ViewHolder>() {
  private var eventTypeList: ArrayList<EventTypes> = ArrayList()


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = RvEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.populate(position, eventTypeList)

  }

  override fun getItemCount(): Int {
    return eventTypeList.size
  }

  class ViewHolder(binding: RvEventBinding) : RecyclerView.ViewHolder(binding.root) {
    private val binding: RvEventBinding

    init {
      this.binding = binding

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

}