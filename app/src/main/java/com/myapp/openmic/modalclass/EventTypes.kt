package com.myapp.openmic.modalclass

data class EventTypes(
  var name: String,
  var image: String?,
  var count: Int = 0,
  var comedyEventList: ArrayList<EventDetails>
) {
  constructor():this("","",0,ArrayList())
}