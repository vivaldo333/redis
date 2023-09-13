package net.ks.dto

case class EventDto(subscriberId: String,
                    eventId: Int,
                    subscriberType: Int,
                    eventTime: String,
                    headers: Option[Map[String, String]] = None,
                    params: Option[Map[String, String]],
                    cid: Option[String] = None)
