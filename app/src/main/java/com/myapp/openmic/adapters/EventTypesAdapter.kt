package com.myapp.openmic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.databinding.RvEventBinding
import com.myapp.openmic.modalclass.EventTypes

class EventTypesAdapter(var context: Context) : RecyclerView.Adapter<EventTypesAdapter.ViewHolder>() {
  private var eventTypeList: ArrayList<EventTypes> = ArrayList()
  var onEventClick: OnClickingMore? = null


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = RvEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view, onEventClick!!)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int,) {
    holder.populate(position, eventTypeList)

  }

  override fun getItemCount(): Int {
    return eventTypeList.size
  }

  inner class ViewHolder(private val binding: RvEventBinding, listener: OnClickingMore) :
    RecyclerView.ViewHolder(binding.root),EventsAdapter.OnEventCardClick {

    init {
      binding.mcvAllEvents.setOnClickListener {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
          listener.onMoreClick(position)
        }
      }

    }

    fun populate(position: Int, eventTypeList: ArrayList<EventTypes>) {

      binding.tvEventType.text = eventTypeList[position].name

      val eventAdapter = EventsAdapter()
      eventAdapter.setOnEventCardClick(this)
      eventAdapter.setEventList(eventTypeList[position].eventList)
      binding.rvEvents.adapter = eventAdapter
    }

    override fun showFullInformation(position: Int) {

    }
  }

  fun setEventTypeList(eventType: ArrayList<EventTypes>) {
    this.eventTypeList = eventType
    notifyDataSetChanged()
  }

  fun setEventClick(onEventCardClick: OnClickingMore) {
    this.onEventClick = onEventCardClick
  }

  interface OnClickingMore {
    fun onMoreClick(position: Int)
  }

}