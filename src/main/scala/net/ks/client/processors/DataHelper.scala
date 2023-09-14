package net.ks.client.processors

import net.ks.dto.EventDto

object DataHelper {
  val eventHeaders = Map("h1" -> Some("v1"), "h2" -> None)
  val eventParameters = Map("p1" -> Some("v01"), "p2" -> Some("v02"))
  def getEvent(eventId: String) = EventDto(
    "380671112233",
    eventId.toInt,
    0,
    "2023-09-14 12:01:59",
    Some(eventHeaders),
    Some(eventParameters),
    "2dfa8408-52f9-11ee-be56-0242ac120002")
}
