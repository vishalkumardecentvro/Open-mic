package com.myapp.openmic.modalclass

import java.io.Serializable

data class Event(
  val eventName: String,
  val type : String,
  val eventLocation: String,
  val longDescription: String,
  val shortDescription: String,
  val time: ArrayList<String>,
  var date: ArrayList<String>,
  val ageCriteria: String,
  val hostedBy: ArrayList<String>,
  val performedBy: ArrayList<String>,
  var eventImageUrl: String,
  var docId : String,
  var price : Int,
  var totalSeats : Int,
  var bookedSeats : Int
) : Serializable  {
  // firebase needs empty constructor
  constructor() : this(
    "", "","", "",
    "", ArrayList<String>(), ArrayList<String>(), "", ArrayList<String>(), ArrayList<String>(), "","",
    0,0,0
  )

}