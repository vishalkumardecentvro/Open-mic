package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.myapp.openmic.adapters.SeatAdapter
import com.myapp.openmic.databinding.FragmentBookTicketBinding


class BookTicketFragment : Fragment() {
  private var _binding: FragmentBookTicketBinding? = null
  private val binding get() = _binding!!
  private var price: Int = 0
  private var totalSeats: Int = 0
  private var dateList: ArrayList<String> = ArrayList()
  private var timeList: ArrayList<String> = ArrayList()
  private var seatAdapter: SeatAdapter? = null

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

      price = bundle.getInt("price")
      totalSeats = bundle.getInt("totalSeats")

      dateList = bundle.getStringArrayList("dateList") as ArrayList<String>
      timeList = bundle.getStringArrayList("timeList") as ArrayList<String>
    }

    seatAdapter = SeatAdapter(requireContext())
    populate()
  }

  private fun initialize() {

    binding.rvSeat.adapter = seatAdapter

  }

  private fun listen() {

  }

  private fun load() {
    val seat: ArrayList<String> = ArrayList()
    for (i in 1..10) {
      seat.add(i.toString())
    }
    seatAdapter?.seatList = seat

  }

  private fun populate() {
    val dateSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
      requireContext(),
      android.R.layout.simple_spinner_item, dateList
    )
    binding.dateSpinner.adapter = dateSpinnerAdapter

    val timeSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter(
      requireContext(),
      android.R.layout.simple_spinner_item, timeList
    )
    binding.timeSpinner.adapter = timeSpinnerAdapter
  }
}