package com.myapp.openmic.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myapp.openmic.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
  private var _binding: FragmentPaymentBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentPaymentBinding.inflate(LayoutInflater.from(context), container, false)
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
    if (bundle != null) {
      populate(bundle)
    }

  }

  private fun initialize() {

  }

  private fun listen() {

  }

  private fun load() {

  }

  private fun populate(bundle: Bundle) {
    binding.tvNameSummary.text = bundle.getString("eventName", "")
    binding.tvDateSummary.text = bundle.getString("date", "")
    binding.tvTimeSummary.text = bundle.getString("time", "")
    binding.tvSeatsSummary.text = "seats : "+bundle.getString("seat", "")

    val seat = bundle.getString("seat", "").toInt()
    val cost = seat * bundle.getInt("price", 1)
    binding.tvCostSummary.text = "Total price : ₹ $cost"

  }
}