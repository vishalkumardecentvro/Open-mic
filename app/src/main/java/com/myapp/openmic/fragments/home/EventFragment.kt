package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.myapp.openmic.Utils
import com.myapp.openmic.databinding.FragmentEventBinding
import com.myapp.openmic.modalclass.Event
import com.squareup.picasso.Picasso

class EventFragment : Fragment() {
  private var _binding: FragmentEventBinding? = null;
  private val binding get() = _binding!!
  private var eventId: String = ""

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
    if (bundle != null && bundle.containsKey("id")) {
      eventId = bundle.getString("id").toString()
    }

  }

  private fun initialize() {

  }

  private fun listen() {

  }

  private fun load() {

    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("events").document(eventId).get()
      .addOnSuccessListener {

        val event : Event? = it.toObject(Event::class.java)
        if (event != null) {
          populate(event)
        }

      }.addOnFailureListener{
        Utils.toast(requireContext(),"Please check internet connection",Utils.TOAST_LENGTH_SHORT)
      }
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