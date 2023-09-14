package net.ks.config

import akka.util.ByteString
import net.ks.dto.{EventDto, EventWrapperDto}
import spray.json._

// Enable JSON format support for your case class
object JsonDtoProtocol extends DefaultJsonProtocol {
  implicit val eventDtoFormat: RootJsonFormat[EventDto] = jsonFormat7(EventDto)
  implicit val eventWrapperDtoFormat: RootJsonFormat[EventWrapperDto] = jsonFormat2(EventWrapperDto)

  def toEventJson(text: ByteString): EventDto = {
    text.utf8String.parseJson.convertTo[EventDto]
  }
}
