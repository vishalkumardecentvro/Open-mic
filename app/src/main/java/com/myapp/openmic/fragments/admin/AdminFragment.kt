package com.myapp.openmic.fragments.admin

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.myapp.openmic.R
import com.myapp.openmic.Utils
import com.myapp.openmic.databinding.FragmentAdminBinding
import java.io.InputStream
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
  private lateinit var firestore: FirebaseFirestore
  private lateinit var firebaseStorage: StorageReference
  private lateinit var bitmap: Bitmap
  private lateinit var path: Uri

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
    firebaseStorage = FirebaseStorage.getInstance().reference

  }

  private fun initialize() {
    editTextList.add(binding.tilEventName)
    editTextList.add(binding.tilLocation)
    editTextList.add(binding.tilShortDescription)

  }

  private fun listen() {

    binding.imageButton.setOnClickListener {
      if (ActivityCompat.checkSelfPermission(
          requireContext(),
          android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
      ) {
        selectImagesFromGallery();

      } else {
        ActivityCompat.requestPermissions(
          requireActivity(),
          arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
          1
        )
      }

    }

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

    binding.mcvTime.setOnClickListener {
      openTimePicker()
    }

    binding.mcvDate.setOnClickListener {
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

  private fun openTimePicker() {
    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR)
    val min = calendar.get(Calendar.MINUTE)

    TimePickerDialog(requireContext(), object : TimePickerDialog.OnTimeSetListener {
      override fun onTimeSet(p0: TimePicker?, hour: Int, min: Int) {
        binding.tvTime.text = "$hour : $min"

      }
    }, hour, min, false).show()
  }

  private fun openDatePicker() {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(requireContext(), { view, year, month, day ->
      binding.tvDate.text = "$day/$month/$year"

    }, year, month, day).show()
  }

  private fun saveEvent() {
    val event = hashMapOf<String, Any>()

    event.put("eventName", binding.tilEventName.editText?.text.toString())
    event.put("type", binding.tilEventType.editText?.text.toString())
    event.put("eventLocation", binding.tilLocation.editText?.text.toString())
    event.put("longDescription", binding.tilLongDescription.editText?.text.toString())
    event.put("shortDescription", binding.tilShortDescription.editText?.text.toString())
    event.put("time", binding.tvTime.text.toString())
    event.put("date", binding.tvDate.text.toString())
    event.put("ageCriteria", "18+")

    event.put("hostedBy", hostList)
    event.put("performedBy", performerList)

    firestore.collection("top-10-events").add(event)
      .addOnSuccessListener {
        Toast.makeText(context, "Event saved", Toast.LENGTH_SHORT).show()
        saveEventImage(it.id)
      }

  }

  private fun selectImagesFromGallery() {
    val galleryIntent = Intent()
    galleryIntent.action = Intent.ACTION_GET_CONTENT
    galleryIntent.type = "image/*"
    startActivityForResult(
      Intent.createChooser(galleryIntent, "Select picture"),
      Utils.PICK_IMAGE
    )
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    Toast.makeText(context, "Image selected", Toast.LENGTH_SHORT).show()

    if (requestCode == Utils.PICK_IMAGE && resultCode == RESULT_OK) {
      val image = data?.data
      path = data?.data!!

      val inputStream: InputStream =
        image?.let { requireContext().contentResolver.openInputStream(it) }!!

      bitmap = BitmapFactory.decodeStream(inputStream)

      binding.ivEvent.setImageBitmap(bitmap)
      binding.ivEvent.visibility = View.VISIBLE
    }
  }

  private fun saveEventImage(id: String) {
    val imageRef = firebaseStorage.child("eventImages/" + System.currentTimeMillis())
    imageRef.putFile(path)
      .addOnSuccessListener {
        Toast.makeText(context, "Media uploaded", Toast.LENGTH_SHORT).show()

        imageRef.downloadUrl.addOnSuccessListener {
          updateEventDocumentWithImage(id, it.toString())
        }

      }.addOnFailureListener {
        Toast.makeText(context, "Failed to upload media", Toast.LENGTH_SHORT).show()
      }
  }

  private fun updateEventDocumentWithImage(id: String, imageUrl: String) {
    val eventImageHash = HashMap<String, Any>()
    eventImageHash.put("eventImageUrl", imageUrl)

    firestore.collection("top-10-events").document(id)
      .update(eventImageHash)

      .addOnSuccessListener {

        Toast.makeText(context, "Image url updated", Toast.LENGTH_SHORT).show()
      }.addOnFailureListener {

        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
      }
  }
}