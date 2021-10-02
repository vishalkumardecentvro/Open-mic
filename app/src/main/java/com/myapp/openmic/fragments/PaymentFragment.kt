package com.myapp.openmic.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myapp.openmic.R
import com.myapp.openmic.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {
  private var _binding : FragmentPaymentBinding ? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentPaymentBinding.inflate(LayoutInflater.from(context),container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    instantiate()
    initialize()
    listen()
    load()
  }

  private fun instantiate(){

  }

  private fun initialize(){

  }

  private fun listen(){

  }

  private fun load(){

  }
}