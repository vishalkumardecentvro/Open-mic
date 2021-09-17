package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.myapp.openmic.adapters.EventAdapter
import com.myapp.openmic.adapters.EventTypesAdapter
import com.myapp.openmic.databinding.FragmentHomeBinding
import com.myapp.openmic.modalclass.EventDetails
import com.myapp.openmic.modalclass.EventTypes
import kotlinx.coroutines.*

class HomeFragment : Fragment() {
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private lateinit var firestore: FirebaseFirestore
  private var eventTypesList: ArrayList<EventTypes>? = null;
  private var comedyEvents: ArrayList<EventDetails>? = null;
  private var eventTypesAdapter: EventTypesAdapter? = null;
  private var eventAdapter: EventAdapter? = null
  private var allEvents: ArrayList<EventTypes>? = null;

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
    firestore = FirebaseFirestore.getInstance()

    eventTypesList = arrayListOf()
    comedyEvents = arrayListOf()

    eventTypesAdapter = EventTypesAdapter()
    eventAdapter = EventAdapter()

  }

  private fun initialize() {
    binding.rvEvent.adapter = eventTypesAdapter
  }

  private fun listen() {

  }

  private fun load() {

    firestore.collection("event-types").get()
      .addOnSuccessListener { it ->

        if (!it.isEmpty) {

          it.forEach {
            val eventTypes: EventTypes = it.toObject()
            eventTypesList?.add(eventTypes)
          }
        }

        firestore.collection("events").get()
          .addOnSuccessListener {

            it.forEach {

              val eventDetails: EventDetails = it.toObject()
              comedyEvents!!.add(eventDetails)
            }

            updateUi()

          }.addOnFailureListener {
            Log.e("--error--", it.printStackTrace().toString())
          }

      }.addOnFailureListener {

        Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT)
          .show()
      }
  }

  private fun updateUi() {
    for (event in eventTypesList!!) {
      event.comedyEventList = comedyEvents!!

    }
    eventTypesAdapter?.setEventTypeList(eventTypesList!!)
    Log.i("--etSize--", eventTypesList!!.size.toString())
  }

}