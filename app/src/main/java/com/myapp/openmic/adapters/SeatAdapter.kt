package com.myapp.openmic.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapp.openmic.R
import com.myapp.openmic.databinding.SeatNumberBinding
import kotlinx.android.synthetic.main.seat_number.view.*

class SeatAdapter(var context: Context, val onSeatClick: onSeatClickInterface) :
  RecyclerView.Adapter<SeatAdapter.ViewHolder>() {
  var currentSeatPosition: Int = -1
  var previousView: View? = null
  var seatList: ArrayList<String> = ArrayList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = SeatNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(view, onSeatClick)

  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.populate(position)
  }

  override fun getItemCount(): Int {
    return seatList.size
  }

  inner class ViewHolder(binding: SeatNumberBinding, onSeatClick: onSeatClickInterface) : RecyclerView.ViewHolder(binding.root) {
    private val binding: SeatNumberBinding = binding

    init {
      binding.tvSeat.setOnClickListener {
        if (currentSeatPosition == -1) {
          currentSeatPosition = adapterPosition
          Log.i("--clickedPosn--", currentSeatPosition.toString())

          previousView = binding.root.tvSeat
          binding.tvSeat.setBackgroundColor(context.resources.getColor(R.color.brand_color_light))
        } else {
          previousView?.setBackgroundColor(context.resources.getColor(R.color.white))

          binding.tvSeat.setBackgroundColor(context.resources.getColor(R.color.brand_color_light))
          currentSeatPosition = adapterPosition
          Log.i("--clickedPosn--", currentSeatPosition.toString())

          previousView = binding.root.tvSeat
        }

        onSeatClick.clickedSeat(currentSeatPosition)
      }
    }

    fun populate(position: Int) {
      binding.tvSeat.text = seatList[position]
    }
  }

  interface onSeatClickInterface {
    fun clickedSeat(currentSeatPosition: Int)
  }
}