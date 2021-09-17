package com.myapp.openmic.modalclass

data class EventTypes(
  var name: String,
  var image: String?,
  var count: Int = 0,
  var eventList: ArrayList<Event>
) {
  constructor():this("","",0, ArrayList())
}