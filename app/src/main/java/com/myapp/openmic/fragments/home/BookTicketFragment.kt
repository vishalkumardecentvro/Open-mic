package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.myapp.openmic.Utils
import com.myapp.openmic.adapters.SeatAdapter
import com.myapp.openmic.databinding.FragmentBookTicketBinding
import com.myapp.openmic.fragments.PaymentFragment


class BookTicketFragment : Fragment(), SeatAdapter.onSeatClickInterface {
  private var _binding: FragmentBookTicketBinding? = null
  private val binding get() = _binding!!
  private var price: Int = 0
  private var totalSeats: Int = 0
  private var dateList: ArrayList<String> = ArrayList()
  private var timeList: ArrayList<String> = ArrayList()
  private var seatAdapter: SeatAdapter? = null
  private var selectedTime: String = ""
  private var selectedDate: String = ""
  private var eventName: String = ""
  private var seat: ArrayList<String> = ArrayList()
  private var selectedSeat: Int = -1;

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
      eventName = bundle.getString("eventName", "")

      dateList = bundle.getStringArrayList("dateList") as ArrayList<String>
      timeList = bundle.getStringArrayList("timeList") as ArrayList<String>
    }

    seatAdapter = SeatAdapter(requireContext(), this)
    populate()
  }

  private fun initialize() {

    binding.rvSeat.adapter = seatAdapter

  }

  private fun listen() {

    binding.paymentButton.setOnClickListener {
      val bundle = Bundle()
      bundle.putString("date", selectedDate)
      bundle.putString("time", selectedTime)
      if (selectedSeat != -1)
        bundle.putString("seat", seat[selectedSeat])

      bundle.putInt("price", price)
      bundle.putString("eventName", eventName)

      val paymentFragment = PaymentFragment()
      paymentFragment.arguments = bundle

      Utils.navigate(requireContext(), paymentFragment, "make payment")
    }

    binding.timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedTime = timeList[p2]
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(requireContext(), "Please select time!", Toast.LENGTH_SHORT).show()
      }

    }

    binding.dateSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedDate = dateList[p2]
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(requireContext(), "Please select date!", Toast.LENGTH_SHORT).show()
      }

    }

  }

  private fun load() {

  }

  private fun populate() {
    for (i in 1..10) {
      seat.add(i.toString())
    }
    seatAdapter?.seatList = seat

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

  override fun clickedSeat(currentSeatPosition: Int) {
    selectedSeat = currentSeatPosition
    Log.i("--seat--",selectedSeat.toString())
  }
}