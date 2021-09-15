package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.myapp.openmic.adapters.EventsAdapter
import com.myapp.openmic.databinding.FragmentHomeBinding
import com.myapp.openmic.modalclass.EventDetails
import com.myapp.openmic.modalclass.EventTypes

class HomeFragment : Fragment() {
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private lateinit var firestore: FirebaseFirestore
  private var eventTypesList: ArrayList<EventTypes>? = null;
  private var eventsAdapter : EventsAdapter ? = null;

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    instantiate()
    initialize()
    listen()
    load()
  }

  private fun instantiate() {
    firestore = FirebaseFirestore.getInstance();
    eventTypesList = arrayListOf()
    eventsAdapter = EventsAdapter()

  }

  private fun initialize() {
    binding.rvEvent.adapter = eventsAdapter
  }

  private fun listen() {

  }

  private fun load() {

    firestore.collection("event-types").get()
      .addOnSuccessListener { it ->
        Log.i("--size--", it.size().toString())
        if (!it.isEmpty) {
          it.forEach {
            val eventTypes: EventTypes = it.toObject()
            eventTypesList?.add(eventTypes)
          }
          eventsAdapter?.setEventTypeList(eventTypesList!!)
          Log.i("--name--", eventTypesList?.get(2)?.name!!)

          Log.i("--Listsize--", eventTypesList?.size.toString())
        }

      }.addOnFailureListener {
        Log.e("--error--","Failed to load event types")

      }

    firestore.collection("events").get()
      .addOnSuccessListener {

        it.forEach {
          Log.i("data", it.data.toString())
          Log.i("id", it.id.toString())
          var eventDetails: EventDetails = it.toObject()
          eventDetails.type
        }

      }.addOnFailureListener {
        Log.e("--error--", it.printStackTrace().toString())
      }


  }
}