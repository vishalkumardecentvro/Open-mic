package com.myapp.openmic.modalclass

data class EventDetails(
  val eventName: String,
  val type : String,
  val eventLocation: String,
  val longDescription: String,
  val shortDescription: String,
  val time: String,
  var date: String,
  val ageCriteria: String,
  val hostedBy: ArrayList<String>,
  val performedBy: ArrayList<String>,
  val eventImageUrl: String
) {
  // firebase needs empty constructor
  constructor() : this(
    "", "","", "",
    "", "", "", "", ArrayList<String>(), ArrayList<String>(), ""
  )
}