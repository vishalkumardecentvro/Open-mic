package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.myapp.openmic.Utils
import com.myapp.openmic.adapters.EventsAdapter
import com.myapp.openmic.adapters.EventTypesAdapter
import com.myapp.openmic.databinding.FragmentHomeBinding
import com.myapp.openmic.modalclass.Event
import com.myapp.openmic.modalclass.EventTypes

class HomeFragment : Fragment(), EventTypesAdapter.OnClickingMore{
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!
  private lateinit var firestore: FirebaseFirestore
  private var eventTypesList: ArrayList<EventTypes>? = null
  private var allEventList: ArrayList<Event>? = null;
  private var eventsAdapter: EventsAdapter = EventsAdapter()
  private var eventTypesAdapter: EventTypesAdapter ? = null

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
    allEventList = arrayListOf()
    eventTypesAdapter = EventTypesAdapter(requireContext())
  }

  private fun initialize() {
    binding.rvEvent.adapter = eventTypesAdapter
    eventTypesAdapter?.setEventClick(this)
  }

  private fun listen() {


  }

  private fun load() {

    firestore.collection("event-types").get()
      .addOnSuccessListener { it ->

        if (!it.isEmpty) {

          it.forEach {
            val eventTypes: EventTypes = it.toObject()

            firestore.collection("top-10-events")
              .whereEqualTo("type", eventTypes.name)
              .get()
              .addOnSuccessListener {

                it.forEach {

                  val event: Event = it.toObject()
                  allEventList!!.add(event)

                }

                eventTypes.eventList = allEventList!!
                eventTypesList?.add(eventTypes)
                updateUi()

                allEventList = ArrayList()

              }.addOnFailureListener {
                Toast.makeText(
                  context,
                  "Please check your internet connection!",
                  Toast.LENGTH_SHORT
                )
                  .show()
              }
          }
        }

      }.addOnFailureListener {

        Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_SHORT)
          .show()
      }
  }

  private fun updateUi() {
    eventTypesAdapter?.setEventTypeList(eventTypesList!!)
  }

  override fun onMoreClick(position: Int) {
    val bundle = Bundle()
    bundle.putString("type", eventTypesList?.get(position)?.name)

    val allEventsFragment = AllEventsFragment()
    allEventsFragment.arguments = bundle

    Utils.navigate(requireContext(), allEventsFragment, "all events")
  }

}