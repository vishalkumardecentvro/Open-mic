package com.myapp.openmic.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.openmic.databinding.FragmentAlleventsBinding

class AllEventsFragment : Fragment() {
  private var _binding: FragmentAlleventsBinding? = null
  private val binding get() = _binding!!
  private var eventToDisplay: String? = null

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
    val bundle = AllEventsFragment().arguments
    if (bundle != null && bundle.containsKey("type")) {
      eventToDisplay = bundle.getString("type")
    }

  }

  private fun initialize() {

  }

  private fun listen() {

  }

  private fun load() {
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("events").whereEqualTo("type", eventToDisplay)
      .get()
      .addOnSuccessListener {

      }.addOnFailureListener {

      }

  }
}