package com.myapp.openmic.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.R
import com.myapp.openmic.databinding.SeatNumberBinding
import kotlinx.android.synthetic.main.seat_number.view.*

class SeatAdapter(var context: Context) : RecyclerView.Adapter<SeatAdapter.ViewHolder>() {
  var previousClickedSeat: Int = -1
  var currentClickedSeat: Int = -1
  var previousView: View? = null
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

    init {
      binding.tvSeat.setOnClickListener {
        if (previousClickedSeat == -1 && currentClickedSeat == -1) {
          currentClickedSeat = adapterPosition
          previousClickedSeat = adapterPosition
          previousView = binding.root.tvSeat
          binding.tvSeat.setBackgroundColor(context.resources.getColor(R.color.brand_color_light))
        } else {
          previousView?.setBackgroundColor(context.resources.getColor(R.color.white))
          binding.tvSeat.setBackgroundColor(context.resources.getColor(R.color.brand_color_light))
          previousView = binding.root.tvSeat
        }

      }
    }

    fun populate(position: Int) {
      binding.tvSeat.text = seatList[position]
    }
  }
}