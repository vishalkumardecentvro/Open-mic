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
import com.myapp.openmic.Utils
import com.myapp.openmic.adapters.EventAdapter
import com.myapp.openmic.databinding.FragmentAlleventsBinding
import com.myapp.openmic.modalclass.Event

class AllEventsFragment : Fragment(), EventAdapter.OnEventCardClick {
  private var _binding: FragmentAlleventsBinding? = null
  private val binding get() = _binding!!
  private var eventToDisplay: String? = null
  private var eventAdapter: EventAdapter? = null
  private var eventList = ArrayList<Event>()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentAlleventsBinding.inflate(inflater, container, false)
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

    if (bundle != null && bundle.containsKey("type")) {
      eventToDisplay = bundle.getString("type")

    }

    eventAdapter = EventAdapter(this)
  }

  private fun initialize() {
    binding.rvAllEvents.adapter = eventAdapter

  }

  private fun listen() {

  }

  private fun load() {
    val firestore = FirebaseFirestore.getInstance()

    firestore.collection("events").whereEqualTo("type", eventToDisplay)
      .get()
      .addOnSuccessListener {
        if (!it.isEmpty) {
          it.forEach {
            val event: Event = it.toObject()
            event.docId = it.id
            eventList.add(event)
          }
        }
        eventAdapter?.setEventList(eventList)

      }.addOnFailureListener {

        Toast.makeText(context, "Please check your internet!", Toast.LENGTH_SHORT).show()
      }
  }

  override fun showFullInformation(position: Int) {
    val bundle = Bundle()
    bundle.putString("id", eventList.get(position).docId)
    val eventsFragment = EventFragment()
    eventsFragment.arguments = bundle
    Utils.navigate(requireContext(), eventsFragment, "event")
  }
}