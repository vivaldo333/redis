package net.ks.dto

import spray.json.DefaultJsonProtocol

case class EventDto(subscriberId: String,
                    eventId: Int,
                    subscriberType: Int,
                    eventTime: String,
                    headers: Option[Map[String, Option[String]]],
                    params: Option[Map[String, Option[String]]],
                    cid: String)