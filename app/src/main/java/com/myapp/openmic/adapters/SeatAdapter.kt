package com.myapp.openmic.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.databinding.SeatNumberBinding

class SeatAdapter : RecyclerView.Adapter<SeatAdapter.ViewHolder>() {
  var seatList: ArrayList<String> = ArrayList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = SeatNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view)

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.populate(position)
  }

  override fun getItemCount(): Int {
    return seatList.size
  }

  inner class ViewHolder(binding: SeatNumberBinding) : RecyclerView.ViewHolder(binding.root) {
    private val binding: SeatNumberBinding = binding
    fun populate(position: Int) {
      binding.tvSeat.text = seatList[position]
    }

  }
}