package com.myapp.openmic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.databinding.EventCardBinding
import com.myapp.openmic.modalclass.EventDetails
import com.squareup.picasso.Picasso

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
  private var eventList : ArrayList<EventDetails> = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = EventCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.populate(position,eventList)

  }

  override fun getItemCount(): Int {
    return eventList.size
  }

  class ViewHolder(binding: EventCardBinding) : RecyclerView.ViewHolder(binding.root) {
    private val binding : EventCardBinding
    init {
      this.binding = binding

    }

    fun populate(position: Int, eventList: ArrayList<EventDetails>) {
      binding.tvEventDescription.text = eventList.get(position).shortDescription
      Picasso.get().load(eventList.get(position).eventImageUrl).fit().into(binding.ivEventImage)
    }

  }

  fun setEventList(event : ArrayList<EventDetails>){
    this.eventList = event
    notifyDataSetChanged()
  }

}