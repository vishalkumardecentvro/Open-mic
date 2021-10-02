package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myapp.openmic.Utils
import com.myapp.openmic.databinding.FragmentEventBinding
import com.myapp.openmic.modalclass.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_event.*

class EventFragment : Fragment() {
  private var _binding: FragmentEventBinding? = null;
  private val binding get() = _binding!!
  private var eventInfo: Event = Event()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    _binding = FragmentEventBinding.inflate(LayoutInflater.from(context), container, false)
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
    val bundle: Bundle? = arguments
    if (bundle != null && bundle.containsKey("event")) {

      eventInfo = bundle.getSerializable("event") as Event
      populate(eventInfo)
    }

  }

  private fun initialize() {

  }

  private fun listen() {
    val bookTicketFragment: BookTicketFragment = BookTicketFragment()
    val bundle = Bundle()

    bundle.putInt("totalSeats", eventInfo.totalSeats)
    bundle.putInt("price", eventInfo.price)
    bundle.putString("eventName", eventInfo.eventName)
    bundle.putInt("bookedSeats", eventInfo.bookedSeats)
    bundle.putStringArrayList("dateList", eventInfo.date)
    bundle.putStringArrayList("timeList", eventInfo.time)

    bookTicketFragment.arguments = bundle

    bookTicketButton.setOnClickListener {
      Utils.navigate(requireContext(), bookTicketFragment, "book ticket")
    }

  }

  private fun load() {

  }

  fun populate(event: Event) {
    binding.tvEventName.text = event.eventName
    //binding.tvDate.text = event.date
    //binding.tvTime.text = event.time
    binding.tvPrice.text = event.price.toString()
    binding.tvLocation.text = event.eventLocation
    binding.tvDescription.text = event.longDescription
    Picasso.get().load(event.eventImageUrl).fit().into(binding.ivEventBanner)
  }

}