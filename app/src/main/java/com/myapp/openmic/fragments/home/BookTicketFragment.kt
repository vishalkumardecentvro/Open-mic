package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myapp.openmic.adapters.SeatAdapter
import com.myapp.openmic.databinding.FragmentBookTicketBinding


class BookTicketFragment : Fragment() {
  private var _binding: FragmentBookTicketBinding? = null
  private val binding get() = _binding!!
  private var price: Int = 0
  private var numberOfSeats: Int = 0
  private val seatAdapter : SeatAdapter = SeatAdapter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentBookTicketBinding.inflate(LayoutInflater.from(context), container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    instantiate()
    initialize()
    listen()
    load()
  }

  private fun instantiate() {
    val bundle = arguments
    if (bundle != null && bundle.containsKey("price")) {
      binding.tvNameSummary.text = bundle.getString("eventName")
      price = bundle.getInt("price")
      numberOfSeats = bundle.getInt("totalSeats")
    }

  }

  private fun initialize() {

    binding.rvSeat.adapter = seatAdapter

  }

  private fun listen() {

  }

  private fun load() {
    val seat : ArrayList<String> = ArrayList()
    for(i in 1..11){
      seat.add(i.toString())
    }
    seatAdapter.seatList = seat

  }
}