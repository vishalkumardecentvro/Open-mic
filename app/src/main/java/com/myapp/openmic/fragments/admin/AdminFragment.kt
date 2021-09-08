package com.myapp.openmic.fragments.admin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.media.metrics.Event
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.FirebaseFirestoreKtxRegistrar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.myapp.openmic.R
import com.myapp.openmic.databinding.FragmentAdminBinding
import com.myapp.openmic.modalclass.EventDetails
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AdminFragment : Fragment() {

  private var _binding: FragmentAdminBinding? = null
  private val binding get() = _binding!!
  private lateinit var ageRestrictionList: ArrayList<String>
  private lateinit var hostList: ArrayList<String>
  private lateinit var performerList: ArrayList<String>
  private lateinit var editTextList: ArrayList<TextInputLayout>
  private lateinit var firestore : FirebaseFirestore

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentAdminBinding.inflate(inflater, container, false)
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

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun instantiate() {
    editTextList = ArrayList()

    ageRestrictionList = ArrayList()
    hostList = ArrayList()
    performerList = ArrayList()

    firestore = Firebase.firestore

  }

  private fun initialize() {
    editTextList.add(binding.tilEventName)
    editTextList.add(binding.tilLocation)
    editTextList.add(binding.tilShortDescription)

  }

  private fun listen() {

    binding.ivAddAge.setOnClickListener {
      if (!binding.tilAge.editText?.text?.trim().isNullOrEmpty())
        binding.cgAgeChipGroup.addView(addAgeToChipView(binding.tilAge.editText?.text.toString()))
      else
        Toast.makeText(context, "Please enter valid age!", Toast.LENGTH_SHORT).show()
    }

    binding.ivAddHost.setOnClickListener {
      if (!binding.tilHostedBy.editText?.text?.trim().isNullOrEmpty())
        binding.cgHostedByChipGroup.addView(addHostToChipView(binding.tilHostedBy.editText?.text.toString()))
      else
        Toast.makeText(context, "Please enter host!", Toast.LENGTH_SHORT).show()
    }

    binding.ivAddPerformers.setOnClickListener {
      if (!binding.tilPerformedBy.editText?.text?.trim().isNullOrEmpty())
        binding.cgPerformedByChipGroup.addView(addPerformerToChipView(binding.tilPerformedBy.editText?.text.toString()))
      else
        Toast.makeText(context, "Please enter performer name!", Toast.LENGTH_SHORT).show()

    }

    binding.mcvTime.setOnClickListener{
      openTimePicker()
    }

    binding.mcvDate.setOnClickListener{
      openDatePicker()
    }
    binding.saveButton.setOnClickListener {
      processSaveEvent()
    }

  }

  private fun load() {

  }

  private fun addAgeToChipView(chipText: String): Chip {
    val chip: Chip = layoutInflater.inflate(R.layout.chip_layout, null) as Chip

    chip.text = chipText
    chip.closeIcon = context?.getDrawable(R.drawable.ic_cancel)
    chip.isCloseIconVisible = true

    ageRestrictionList.add(chipText)

    chip.setOnCloseIconClickListener { view ->
      binding.cgAgeChipGroup.removeView(view)
      ageRestrictionList.remove(chip.text)
    }
    return chip
  }

  private fun addHostToChipView(chipText: String): Chip {
    val chip: Chip = layoutInflater.inflate(R.layout.chip_layout, null) as Chip

    chip.text = chipText
    chip.closeIcon = context?.getDrawable(R.drawable.ic_cancel)
    chip.isCloseIconVisible = true

    hostList.add(chipText)

    chip.setOnCloseIconClickListener { view ->
      binding.cgHostedByChipGroup.removeView(view)
      hostList.remove(chip.text)
    }
    return chip
  }

  private fun addPerformerToChipView(text: String): Chip {
    val chip: Chip = layoutInflater.inflate(R.layout.chip_layout, null) as Chip

    chip.text = text
    chip.closeIcon = context?.getDrawable(R.drawable.ic_cancel)
    chip.isCloseIconVisible = true

    performerList.add(text)

    chip.setOnCloseIconClickListener { view ->
      binding.cgPerformedByChipGroup.removeView(view)
      performerList.remove(chip.text)
    }

    return chip
  }

  private fun processSaveEvent() {

    for (tilEditText in editTextList) {
      if (tilEditText.editText?.text.isNullOrEmpty()) {
        Toast.makeText(context, "Please enter ${tilEditText.editText?.hint}", Toast.LENGTH_SHORT)
          .show()
        return
      }
    }

    saveEvent()
  }

  private fun openTimePicker(){
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR)
    val min = calendar.get(Calendar.MINUTE)

    val timePicker = TimePickerDialog(requireContext(),object : TimePickerDialog.OnTimeSetListener{
      override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        binding.tvTime.setText("$hour : $min")

      }
    },hour,min,false).show()

  }

  private fun openDatePicker(){
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = DatePickerDialog(requireContext(),{ view, year, month, day ->
      binding.tvDate.setText("$day/$month/$year")

    },year,month,day).show()

  }

  private fun saveEvent(){
    val eventObject : EventDetails = EventDetails(
      binding.tilEventName.editText?.text.toString(),
      binding.tilLocation.editText?.text.toString(),
      binding.tilLongDescription.editText?.text.toString(),
      binding.tilShortDescription.editText?.text.toString(),
      binding.tvTime.text.toString(),
      binding.tvDate.text.toString(),
      "18+",
      hostList,
      performerList
    )

    val event = hashMapOf<String,EventDetails>()
    event.put("object",eventObject)

    firestore.collection("events/Roy78jWH1ql2tnZ0galR/comedyEvents").add(event).addOnSuccessListener {
      Toast.makeText(context,"Event saved",Toast.LENGTH_SHORT).show()
    }

  }

}