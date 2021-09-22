package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myapp.openmic.databinding.FragmentEventBinding
import com.myapp.openmic.modalclass.Event
import com.squareup.picasso.Picasso

class EventFragment : Fragment() {
  private var _binding: FragmentEventBinding? = null;
  private val binding get() = _binding!!

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

      val eventInfo: Event = bundle.getSerializable("event") as Event
      populate(eventInfo)
    }

  }

  private fun initialize() {

  }

  private fun listen() {

  }

  private fun load() {

  }

  fun populate(event: Event) {
    binding.tvEventName.text = event.eventName
    binding.tvDate.text = event.date
    binding.tvTime.text = event.time
    binding.tvLocation.text = event.eventLocation
    binding.tvDescription.text = event.longDescription
    Picasso.get().load(event.eventImageUrl).fit().into(binding.ivEventBanner)
  }
}